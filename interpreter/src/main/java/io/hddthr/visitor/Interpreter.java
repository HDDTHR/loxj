package io.hddthr.visitor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import io.hddthr.exception.InterpreterException;
import io.hddthr.model.Expr;
import io.hddthr.model.Expr.Assign;
import io.hddthr.model.Expr.Binary;
import io.hddthr.model.Expr.Grouping;
import io.hddthr.model.Expr.Literal;
import io.hddthr.model.Expr.Unary;
import io.hddthr.model.Expr.Variable;
import io.hddthr.model.Stmt.Block;
import io.hddthr.model.Stmt.Expression;
import io.hddthr.model.Stmt.Print;
import io.hddthr.model.Stmt.Var;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Interpreter implements Visitor<Object> {

  private Environment environment = new Environment();

  @Override
  public Object visitAssignExpr(Assign expr) {
    return environment.assign(expr.name.getLexeme(), evaluate(expr.value));
  }

  @Override
  public Object visitBinaryExpr(Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right);
    switch (expr.operator.getType()) {
      case MINUS:
        assertNumber(left, right);
        return (double) left - (double) right;
      case PLUS:
        if (left instanceof Double l && right instanceof Double r) {
          return l + r;
        }
        if (left instanceof String l && right instanceof String r) {
          return l.concat(r);
        }
        throw new InterpreterException("Operands must be two numbers or two strings.");
      case SLASH:
        assertNumber(left, right);
        if (right.equals(0.0)) {
          throw new InterpreterException("Division by zero.");
        }
        return (double) left / (double) right;
      case STAR:
        assertNumber(left, right);
        return (double) left * (double) right;
      case GREATER:
        assertNumber(left, right);
        return (double) left > (double) right;
      case GREATER_EQUAL:
        assertNumber(left, right);
        return (double) left >= (double) right;
      case LESS:
        assertNumber(left, right);
        return (double) left < (double) right;
      case LESS_EQUAL:
        assertNumber(left, right);
        return (double) left <= (double) right;
      case EQUAL_EQUAL:
        return isEqual(left, right);
      case BANG_EQUAL:
        return !left.equals(right);
    }
    return null;
  }

  private void assertNumber(Object... objs) {
    if (!Arrays.stream(objs).allMatch(o -> o instanceof Double)) {
      throw new InterpreterException("Operands must be numbers.");
    }
  }

  private Object isEqual(Object left, Object right) {
    if (isNull(left) && isNull(right)) {
      return true;
    }
    if (isNull(left)) {
      return false;
    }
    return left.equals(right);
  }

  @Override
  public Object visitGroupingExpr(Grouping expr) {
    return evaluate(expr.expression);
  }

  @Override
  public Object visitLiteralExpr(Literal expr) {
    return expr.value;
  }

  @Override
  public Object visitUnaryExpr(Unary expr) {
    Object right = evaluate(expr.right);
    return switch (expr.operator.getType()) {
      case MINUS -> {
        assertNumber(right);
        yield -(double) right;
      }
      case BANG -> !isTruthy(right);
      default -> null;
    };
  }

  @Override
  public Object visitVariableExpr(Variable expr) {
    return environment.get(expr.name.getLexeme());
  }

  @Override
  public Object visitExpressionStmt(Expression stmt) {
    return evaluate(stmt.expression);
  }

  @Override
  public Object visitPrintStmt(Print stmt) {
    Object value = evaluate(stmt.expression);
    System.out.println(stringify(value));
    return null;
  }

  @Override
  public Object visitVarStmt(Var stmt) {
    Object value = null;
    if (stmt.initializer != null) {
      value = evaluate(stmt.initializer);
    }
    environment.define(stmt.name.getLexeme(), value);
    return null;
  }

  @Override
  public Object visitBlockStmt(Block stmt) {
    Environment previous = this.environment;
    this.environment = new Environment(previous);
    try {
      stmt.statements.forEach(s -> s.accept(this));
    } finally {
      this.environment = previous;
    }
    return null;
  }

  private String stringify(Object value) {
    if (value == null) {
      return "nil";
    }
    return value.toString();
  }

  private boolean isTruthy(Object right) {
    if (right == null) {
      return false;
    }
    if (right instanceof Boolean) {
      return (boolean) right;
    }
    return true;
  }

  private Object evaluate(Expr expr) {
    return expr.accept(this);
  }

  @AllArgsConstructor
  @NoArgsConstructor
  static class Environment {

    private final Map<String, Object> map = new HashMap<>();
    private Environment parent;

    public void define(String name, Object value) {
      map.put(name, value);
    }

    public Object assign(String name, Object value) {
      if (map.containsKey(name)) {
        map.put(name, value);
        return value;
      }
      if (nonNull(parent)) {
        return parent.assign(name, value);
      }
      throw new InterpreterException("Undefined variable '" + name + "'.");
    }

    public Object get(String name) {
      if (map.containsKey(name)) {
        return map.get(name);
      }
      if (nonNull(parent)) {
        return parent.get(name);
      }
      throw new InterpreterException("Undefined variable '" + name + "'.");
    }

  }

}

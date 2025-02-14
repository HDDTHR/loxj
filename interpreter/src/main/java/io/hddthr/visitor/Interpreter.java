package io.hddthr.visitor;

import static java.util.Objects.isNull;

import io.hddthr.model.Expr;
import io.hddthr.model.Expr.Binary;
import io.hddthr.model.Expr.Grouping;
import io.hddthr.model.Expr.Literal;
import io.hddthr.model.Expr.Unary;
import java.util.Arrays;

public class Interpreter implements Visitor<Object> {

  public Object interpret(Expr expr) {
    try {
      return evaluate(expr);
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private Object evaluate(Expr expr) {
    return expr.accept(this);
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
        throw new RuntimeException("Operands must be two numbers or two strings.");
      case SLASH:
        assertNumber(left, right);
        if (right.equals(0.0)) {
          throw new RuntimeException("Division by zero.");
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

  private boolean isTruthy(Object right) {
    if (right == null) {
      return false;
    }
    if (right instanceof Boolean) {
      return (boolean) right;
    }
    return true;
  }

  private void assertNumber(Object... objs) {
    if (!Arrays.stream(objs).allMatch(o -> o instanceof Double)) {
      throw new RuntimeException("Operands must be numbers.");
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
}

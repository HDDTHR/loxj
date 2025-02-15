package io.hddthr.visitor;

import io.hddthr.model.Expr;
import io.hddthr.model.Expr.Assign;
import io.hddthr.model.Expr.Variable;
import io.hddthr.model.Stmt.Block;
import io.hddthr.model.Stmt.Expression;
import io.hddthr.model.Stmt.If;
import io.hddthr.model.Stmt.Print;
import io.hddthr.model.Stmt.Var;
import io.hddthr.model.Token;
import io.hddthr.model.TokenType;
import java.util.stream.Collectors;

public class PrintVisitor implements Visitor<String> {

  public static void main(String[] args) {
    Expr e = new Expr.Binary(
        new Expr.Unary(new Token(TokenType.MINUS, 1, "-", null), new Expr.Literal(123)),
        new Token(TokenType.STAR, 1, "*", null), new Expr.Grouping(new Expr.Literal(150)));

    System.out.println(e.accept(new PrintVisitor()));
  }

  @Override
  public String visitAssignExpr(Assign expr) {
    return parenthesise("assign " + expr.name.getLexeme() + " = " + expr.value.accept(this));
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesise(
        expr.left.accept(this) + expr.operator.getLexeme() + expr.right.accept(this));
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesise(expr.expression.accept(this));
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    return parenthesise(expr.value.toString());
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesise(expr.operator.getLexeme() + expr.right.accept(this));
  }

  @Override
  public String visitVariableExpr(Variable expr) {
    return parenthesise(expr.name.getLexeme());
  }

  @Override
  public String visitExpressionStmt(Expression stmt) {
    return parenthesise(stmt.expression.accept(this));
  }

  @Override
  public String visitPrintStmt(Print stmt) {
    return parenthesise("print: " + stmt.expression.accept(this));
  }

  @Override
  public String visitVarStmt(Var stmt) {
    return parenthesise(stmt.name.getLexeme() + " = " + stmt.initializer.accept(this));
  }

  @Override
  public String visitIfStmt(If stmt) {
    return parenthesise(
        "if " + stmt.condition.accept(this) + " then " + stmt.thenBranch.accept(this) + " else "
            + stmt.elseBranch.accept(this));
  }

  @Override
  public String visitBlockStmt(Block stmt) {
    return "block {" + stmt.statements.stream().map(s -> s.accept(this))
        .collect(Collectors.joining("")) + "}";
  }

  private String parenthesise(String str) {
    return "(" + str + ")";
  }
}

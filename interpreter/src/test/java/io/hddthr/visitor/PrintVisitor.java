package io.hddthr.visitor;

import io.hddthr.model.Expr;
import io.hddthr.model.Expr.Variable;
import io.hddthr.model.Stmt.Expression;
import io.hddthr.model.Stmt.Print;
import io.hddthr.model.Stmt.Var;
import io.hddthr.model.Token;
import io.hddthr.model.TokenType;

public class PrintVisitor implements Visitor<String> {

  public static void main(String[] args) {
    Expr e = new Expr.Binary(
        new Expr.Unary(new Token(TokenType.MINUS, 1, "-", null), new Expr.Literal(123)),
        new Token(TokenType.STAR, 1, "*", null), new Expr.Grouping(new Expr.Literal(150)));

    System.out.println(e.accept(new PrintVisitor()));
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesise(
        expr.left.accept(this) + expr.operator.getLexeme() + expr.right.accept(this));
  }

  private String parenthesise(String str) {
    return " " + str + " ";
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return " (" + expr.expression.accept(this) + ") ";
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    return parenthesise(expr.value.toString());
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return expr.operator.getLexeme() + expr.right.accept(this);
  }

  @Override
  public String visitVariableExpr(Variable expr) {
    return expr.name.getLexeme();
  }

  @Override
  public String visitExpressionStmt(Expression stmt) {
    return stmt.expression.accept(this);
  }

  @Override
  public String visitPrintStmt(Print stmt) {
    return "print: " + stmt.expression.accept(this);
  }

  @Override
  public String visitVarStmt(Var stmt) {
    return stmt.name.getLexeme() + " = " + stmt.initializer.accept(this);
  }
}

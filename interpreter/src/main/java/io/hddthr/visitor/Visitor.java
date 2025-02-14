package io.hddthr.visitor;

import io.hddthr.model.Stmt;
import io.hddthr.model.Expr;

public interface Visitor<R> {
  R visitBinaryExpr(Expr.Binary expr);
  R visitGroupingExpr(Expr.Grouping expr);
  R visitLiteralExpr(Expr.Literal expr);
  R visitUnaryExpr(Expr.Unary expr);

  R visitExpressionStmt(Stmt.Expression stmt);
  R visitPrintStmt(Stmt.Print stmt);
}
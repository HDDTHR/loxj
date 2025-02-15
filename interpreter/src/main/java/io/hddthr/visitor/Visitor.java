package io.hddthr.visitor;

import io.hddthr.model.Stmt;
import io.hddthr.model.Expr;

public interface Visitor<R> {
  R visitAssignExpr(Expr.Assign expr);
  R visitBinaryExpr(Expr.Binary expr);
  R visitGroupingExpr(Expr.Grouping expr);
  R visitLiteralExpr(Expr.Literal expr);
  R visitUnaryExpr(Expr.Unary expr);
  R visitVariableExpr(Expr.Variable expr);

  R visitExpressionStmt(Stmt.Expression stmt);
  R visitPrintStmt(Stmt.Print stmt);
  R visitVarStmt(Stmt.Var stmt);
  R visitBlockStmt(Stmt.Block stmt);
}
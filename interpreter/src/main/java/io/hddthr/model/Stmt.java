package io.hddthr.model;

import lombok.RequiredArgsConstructor;
import io.hddthr.visitor.Visitor;

public abstract class Stmt {

  @RequiredArgsConstructor
  public static class Expression extends Stmt {
    public final Expr expression;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitExpressionStmt(this);
    }
  }
  @RequiredArgsConstructor
  public static class Print extends Stmt {
    public final Expr expression;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitPrintStmt(this);
    }
  }
  @RequiredArgsConstructor
  public static class Var extends Stmt {
    public final Token name;
    public final Expr initializer;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitVarStmt(this);
    }
  }

  public abstract <R> R accept(Visitor<R> visitor);
}

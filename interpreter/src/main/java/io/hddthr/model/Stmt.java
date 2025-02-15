package io.hddthr.model;

import java.util.List;
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
  @RequiredArgsConstructor
  public static class While extends Stmt {
    public final Expr condition;
    public final Stmt statement;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitWhileStmt(this);
    }
  }
  @RequiredArgsConstructor
  public static class If extends Stmt {
    public final Expr condition;
    public final Stmt thenBranch;
    public final Stmt elseBranch;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitIfStmt(this);
    }
  }
  @RequiredArgsConstructor
  public static class Block extends Stmt {
    public final List<Stmt> statements;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitBlockStmt(this);
    }
  }

  public abstract <R> R accept(Visitor<R> visitor);
}

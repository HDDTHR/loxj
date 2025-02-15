package io.hddthr.model;

import lombok.RequiredArgsConstructor;
import io.hddthr.visitor.Visitor;

public abstract class Expr {

  @RequiredArgsConstructor
  public static class Binary extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitBinaryExpr(this);
    }
  }
  @RequiredArgsConstructor
  public static class Grouping extends Expr {
    public final Expr expression;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitGroupingExpr(this);
    }
  }
  @RequiredArgsConstructor
  public static class Literal extends Expr {
    public final Object value;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitLiteralExpr(this);
    }
  }
  @RequiredArgsConstructor
  public static class Unary extends Expr {
    public final Token operator;
    public final Expr right;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitUnaryExpr(this);
    }
  }
  @RequiredArgsConstructor
  public static class Variable extends Expr {
    public final Token name;

    @Override
    public <R> R accept(Visitor<R> visitor){
      return visitor.visitVariableExpr(this);
    }
  }

  public abstract <R> R accept(Visitor<R> visitor);
}
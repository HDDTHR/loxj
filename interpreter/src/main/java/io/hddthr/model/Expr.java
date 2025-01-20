package io.hddthr.model;

import lombok.AllArgsConstructor;

public abstract class Expr {
  @AllArgsConstructor
  static class Binary extends Expr {
    final private Expr left;
    final private Token operator;
    final private Expr right;
  }

  @AllArgsConstructor
  public static class Grouping extends Expr {
    final private Expr expression;
  }

  @AllArgsConstructor
  static class Literal extends Expr {
    final private Object value;
  }

  @AllArgsConstructor
  static class Unary extends Expr {
    final private Token operator;
    final private Expr right;
  }
}

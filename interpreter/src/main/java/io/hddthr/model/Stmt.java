package io.hddthr.model;

import io.hddthr.visitor.Visitor;

public abstract class Stmt {
    public static class Expression extends Stmt {
        public final Expr expression;

        public Expression(Expr expression){
            this.expression = expression;
        }

        @Override
        public <R> R accept(Visitor<R> visitor){
            return visitor.visitExpressionStmt(this);
        }
    }
    public static class Print extends Stmt {
        public final Expr expression;

        public Print(Expr expression){
            this.expression = expression;
        }

        @Override
        public <R> R accept(Visitor<R> visitor){
            return visitor.visitPrintStmt(this);
        }
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
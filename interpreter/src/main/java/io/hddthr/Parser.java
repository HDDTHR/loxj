package io.hddthr;

import static io.hddthr.model.TokenType.FALSE;
import static io.hddthr.model.TokenType.LEFT_PAREN;
import static io.hddthr.model.TokenType.NIL;
import static io.hddthr.model.TokenType.NUMBER;
import static io.hddthr.model.TokenType.RIGHT_PAREN;
import static io.hddthr.model.TokenType.SEMICOLON;
import static io.hddthr.model.TokenType.STRING;
import static io.hddthr.model.TokenType.TRUE;

import io.hddthr.exception.ParserException;
import io.hddthr.model.Expr;
import io.hddthr.model.Expr.Binary;
import io.hddthr.model.ParsingError;
import io.hddthr.model.Stmt;
import io.hddthr.model.Token;
import io.hddthr.model.TokenType;
import io.hddthr.reader.TokenReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Parser {

  private TokenReader reader;
  private List<ParsingError> errors;

  public List<Stmt> parse(List<Token> tokens) {
    reader = new TokenReader(tokens);
    errors = new ArrayList<>();

    List<Stmt> statements = new ArrayList<>();
    while (!reader.isAtEnd()) {
      statements.add(declaration());
    }
    if(!errors.isEmpty())
      throw new ParserException(errors);
    return statements;
  }

  private Stmt declaration() {
    try {
      if (reader.match(TokenType.VAR)) {
        return varDeclaration();
      }
      return statement();
    } catch (RuntimeException e) {
      synchronize();
      return null;
    }
  }

  private Stmt varDeclaration() {
    Token name = consume(TokenType.IDENTIFIER, "Expect variable name.");
    Expr initializer = null;
    if (reader.match(TokenType.EQUAL)) {
      initializer = expression();
    }
    consume(SEMICOLON, "Expect ';' after variable declaration.");
    return new Stmt.Var(name, initializer);
  }

  private Token consume(TokenType identifier, String s) {
    if (!reader.match(identifier)) {
      reportError(s);
    }
    return reader.previous();
  }

  private Stmt statement() {
    if (reader.match(TokenType.PRINT)) {
      return printStatement();
    }
    return expressionStatement();
  }

  private Stmt expressionStatement() {
    Expr value = expression();
    consume(SEMICOLON, "Expect ';' after value.");
    return new Stmt.Expression(value);
  }

  private Stmt printStatement() {
    Expr value = expression();
    consume(SEMICOLON, "Expect ';' after value.");
    return new Stmt.Print(value);
  }

  private Expr expression() {
    return equality();
  }

  private Expr equality() {
    Expr expr = comparison();
    while (reader.match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)) {
      Token operator = reader.previous();
      Expr right = comparison();
      expr = new Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr comparison() {
    Expr expr = term();
    while (reader.match(TokenType.GREATER, TokenType.LESS, TokenType.GREATER_EQUAL,
        TokenType.LESS_EQUAL)) {
      Token operator = reader.previous();
      Expr right = term();
      expr = new Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr term() {
    Expr expr = factor();
    while (reader.match(TokenType.PLUS, TokenType.MINUS)) {
      Token operator = reader.previous();
      Expr right = factor();
      expr = new Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr factor() {
    Expr expr = unary();
    while (reader.match(TokenType.STAR, TokenType.SLASH)) {
      Token operator = reader.previous();
      Expr right = unary();
      expr = new Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr unary() {
    if (reader.match(TokenType.BANG, TokenType.MINUS)) {
      Token operator = reader.previous();
      Expr right = unary();
      return new Expr.Unary(operator, right);
    }
    return primary();
  }

  private Expr primary() {
    if (reader.match(FALSE)) {
      return new Expr.Literal(false);
    }
    if (reader.match(TRUE)) {
      return new Expr.Literal(true);
    }
    if (reader.match(NIL)) {
      return new Expr.Literal(null);
    }

    if (reader.match(NUMBER, STRING)) {
      return new Expr.Literal(reader.previous().getLiteral());
    }

    if (reader.match(TokenType.IDENTIFIER)) {
      return new Expr.Variable(reader.previous());
    }

    if (reader.match(LEFT_PAREN)) {
      Expr expr = expression();
      consume(RIGHT_PAREN, "Expect ')' after expression.");
      return new Expr.Grouping(expr);
    }
    reportError("Expect expression.");
    return null;
  }

  private void synchronize() {
    reader.advance();

    while (!reader.isAtEnd()) {
      if (reader.previous().getType() == SEMICOLON) {
        return;
      }

      switch (reader.peek(1).getType()) {
        case CLASS:
        case FUNC:
        case VAR:
        case FOR:
        case IF:
        case WHILE:
        case PRINT:
        case RETURN:
          return;
      }
      reader.advance();
    }
  }

  private void reportError(String s) {
    errors.add(new ParsingError(reader.previous(), s));
    throw new RuntimeException();
  }

}


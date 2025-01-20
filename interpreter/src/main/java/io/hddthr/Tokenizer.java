package io.hddthr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import io.hddthr.model.Token;
import io.hddthr.model.TokenType;

public class Tokenizer {
  private final Map<String, TokenType> keywords = new HashMap<>() {{
    put("and", TokenType.AND);
    put("or", TokenType.OR);
    put("if", TokenType.IF);
    put("else", TokenType.ELSE);
    put("for", TokenType.FOR);
    put("false", TokenType.FALSE);
    put("true", TokenType.TRUE);
    put("var", TokenType.VAR);
    put("print", TokenType.PRINT);
    put("class", TokenType.CLASS);
    put("nil", TokenType.NIL);
    put("return", TokenType.RETURN);
    put("super", TokenType.SUPER);
    put("while", TokenType.WHILE);
    put("this", TokenType.THIS);
    put("func", TokenType.FUNC);
    put("new", TokenType.NEW);
  }};
  private List<Token> tokens;
  private Reader reader;

  public List<Token> tokenize(String code) {
    if (Objects.isNull(code)) return List.of(new Token(TokenType.EOF, 1));
    reader = new Reader(code);
    tokens = new ArrayList<>();
    while (!reader.reachedEOF()) {
      reader.resetStartPosition();
      getNextToken();
      reader.advance();
    }
    tokens.add(new Token(TokenType.EOF, reader.getLine(), null, null));
    return tokens;
  }

  @SuppressWarnings("t")
  private void getNextToken() {
    char c = reader.getCurrentCharacter();
    switch (c) {
      case '(':
        addToken(TokenType.LEFT_PAREN);
        break;
      case ')':
        addToken(TokenType.RIGHT_PAREN);
        break;
      case '{':
        addToken(TokenType.LEFT_BRACE);
        break;
      case '}':
        addToken(TokenType.RIGHT_BRACE);
        break;
      case '.':
        addToken(TokenType.DOT);
        break;
      case '-':
        addToken(TokenType.DASH);
        break;
      case '+':
        addToken(TokenType.PLUS);
        break;
      case ',':
        addToken(TokenType.COMMA);
        break;
      case ';':
        addToken(TokenType.SEMICOLON);
        break;
      case '*':
        addToken(TokenType.STAR);
        break;
      case '=':
        if (reader.nextMatches('=')) {
          reader.advance();
          addToken(TokenType.EQUAL_EQUAL);
        } else addToken(TokenType.EQUAL);
        break;
      case '!':
        if (reader.nextMatches('=')) {
          reader.advance();
          addToken(TokenType.BANG_EQUAL);
        } else addToken(TokenType.BANG);
        break;
      case '<':
        if (reader.nextMatches('=')) {
          reader.advance();
          addToken(TokenType.LESS_EQUAL);
        } else addToken(TokenType.LESS);
        break;
      case '>':
        if (reader.nextMatches('=')) {
          reader.advance();
          addToken(TokenType.GREATER_EQUAL);
        } else addToken(TokenType.GREATER);
        break;
      case '"':
        addStringToken();
        break;
      case '/':
        if (reader.nextMatches('/')) {
          while (!reader.reachedEOF() && reader.peek(1) != '\n') reader.advance();
        } else addToken(TokenType.SLASH);
        break;
      case '\n':
        reader.addLine();
        break;
      case ' ':
      case '\t':
      case '\r':
        break;
      default:
        if (isDigit(c)) addNumberToken();
        else if (isAlpha(c)) addKeywordToken();
        else throw new IllegalArgumentException(
              String.format("Illegal character at line %d, character %d", reader.getLine(),
                  reader.getIndexRelativeToLine()));
    }
  }

  private void addToken(TokenType type) {
    tokens.add(new Token(type, reader.getLine(), reader.getSubstringToIndex(), null));
  }

  private void addStringToken() {
    reader.advance();
    while (!reader.reachedEOF()) {
      char currentChar = reader.getCurrentCharacter();
      if (currentChar == '"') break;
      if (currentChar == '\n') throw getUnterminatedStringException();
      reader.advance();
    }
    if (reader.reachedEOF()) throw getUnterminatedStringException();
    String text = reader.getSubstringToIndex();
    tokens.add(new Token(TokenType.STRING, reader.getLine(), text, text.substring(1, text.length() - 1)));
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private void addNumberToken() {
    if (reader.peekBack() == '.') throw getInvalidNumberException();
    while (isDigit(reader.peek(1))) reader.advance();
    if (reader.peek(1) == '.' && isDigit(reader.peek(2))) {
      do reader.advance(); while (isDigit(reader.peek(1)));
    }
    checkInvalidPostfix();
    String text = reader.getSubstringToIndex();
    tokens.add(new Token(TokenType.NUMBER, reader.getLine(), text, Double.valueOf(text)));
  }

  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
  }

  private void addKeywordToken() {
    while (isAlphaNumeric(reader.peek(1))) reader.advance();
    String text = reader.getSubstringToIndex();
    Optional.ofNullable(keywords.get(text)).ifPresentOrElse(this::addToken,
        () -> tokens.add(new Token(TokenType.IDENTIFIER, reader.getLine(), text, null)));
  }

  private IllegalArgumentException getUnterminatedStringException() {
    return new IllegalArgumentException(String.format("Unterminated string at line %d, character %d", reader.getLine(),
        reader.getStartIndexRelativeToLine()));
  }

  private IllegalArgumentException getInvalidNumberException() {
    return new IllegalArgumentException(String.format("Invalid number at line %d, character %d", reader.getLine(),
        reader.getStartIndexRelativeToLine()));
  }

  private void checkInvalidPostfix() {
    char nextChar = reader.peek(1);
    if (!isOperator(nextChar) && !";), \0".contains(String.valueOf(nextChar))) throw getInvalidNumberException();
  }

  private boolean isAlphaNumeric(char c) {
    return isAlpha(c) || isDigit(c);
  }

  private boolean isOperator(char c) {
    return "+-/*<=>=!=".contains(Character.toString(c));
  }
}

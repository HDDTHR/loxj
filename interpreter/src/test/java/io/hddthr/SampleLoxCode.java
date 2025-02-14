package io.hddthr;

import static io.hddthr.model.TokenType.AND;
import static io.hddthr.model.TokenType.BANG;
import static io.hddthr.model.TokenType.CLASS;
import static io.hddthr.model.TokenType.COMMA;
import static io.hddthr.model.TokenType.DOT;
import static io.hddthr.model.TokenType.ELSE;
import static io.hddthr.model.TokenType.EOF;
import static io.hddthr.model.TokenType.EQUAL;
import static io.hddthr.model.TokenType.EQUAL_EQUAL;
import static io.hddthr.model.TokenType.FALSE;
import static io.hddthr.model.TokenType.FOR;
import static io.hddthr.model.TokenType.FUNC;
import static io.hddthr.model.TokenType.IDENTIFIER;
import static io.hddthr.model.TokenType.IF;
import static io.hddthr.model.TokenType.LEFT_BRACE;
import static io.hddthr.model.TokenType.LEFT_PAREN;
import static io.hddthr.model.TokenType.LESS;
import static io.hddthr.model.TokenType.LESS_EQUAL;
import static io.hddthr.model.TokenType.MINUS;
import static io.hddthr.model.TokenType.NEW;
import static io.hddthr.model.TokenType.NIL;
import static io.hddthr.model.TokenType.NUMBER;
import static io.hddthr.model.TokenType.OR;
import static io.hddthr.model.TokenType.PLUS;
import static io.hddthr.model.TokenType.PRINT;
import static io.hddthr.model.TokenType.RETURN;
import static io.hddthr.model.TokenType.RIGHT_BRACE;
import static io.hddthr.model.TokenType.RIGHT_PAREN;
import static io.hddthr.model.TokenType.SEMICOLON;
import static io.hddthr.model.TokenType.SLASH;
import static io.hddthr.model.TokenType.STAR;
import static io.hddthr.model.TokenType.STRING;
import static io.hddthr.model.TokenType.SUPER;
import static io.hddthr.model.TokenType.THIS;
import static io.hddthr.model.TokenType.TRUE;
import static io.hddthr.model.TokenType.VAR;
import static io.hddthr.model.TokenType.WHILE;
import static java.util.Objects.requireNonNull;

import io.hddthr.model.Token;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleLoxCode {

  private static final Map<String, List<Token>> parsedCode;

  static {
    parsedCode = new HashMap<>();
    parsedCode.put("boolean-test",
        List.of(new Token(VAR, 1, "var", null), new Token(IDENTIFIER, 1, "variable1", null),
            new Token(EQUAL, 1, "=", null), new Token(TRUE, 1, "true", null),
            new Token(SEMICOLON, 1, ";", null),
            new Token(VAR, 2, "var", null), new Token(IDENTIFIER, 2, "variable2", null),
            new Token(EQUAL, 2, "=", null),
            new Token(FALSE, 2, "false", null), new Token(SEMICOLON, 2, ";", null),
            new Token(PRINT, 4, "print", null),
            new Token(BANG, 4, "!", null), new Token(IDENTIFIER, 4, "variable1", null),
            new Token(SEMICOLON, 4, ";", null),
            new Token(PRINT, 5, "print", null), new Token(IDENTIFIER, 5, "variable1", null),
            new Token(AND, 5, "and", null),
            new Token(IDENTIFIER, 5, "variable2", null), new Token(SEMICOLON, 5, ";", null),
            new Token(PRINT, 6, "print", null), new Token(IDENTIFIER, 6, "variable1", null),
            new Token(OR, 6, "or", null),
            new Token(IDENTIFIER, 6, "variable2", null), new Token(SEMICOLON, 6, ";", null),
            new Token(PRINT, 7, "print", null), new Token(NIL, 7, "nil", null),
            new Token(EQUAL_EQUAL, 7, "==", null),
            new Token(NIL, 7, "nil", null), new Token(SEMICOLON, 7, ";", null),
            new Token(EOF, 7, null, null)));
    parsedCode.put("calculate-tax",
        List.of(new Token(VAR, 1, "var", null), new Token(IDENTIFIER, 1, "p1", null),
            new Token(EQUAL, 1, "=", null),
            new Token(NUMBER, 1, "16.5", 16.5), new Token(SEMICOLON, 1, ";", null),
            new Token(VAR, 2, "var", null),
            new Token(IDENTIFIER, 2, "p2", null), new Token(EQUAL, 2, "=", null),
            new Token(NUMBER, 2, "19", 19.0),
            new Token(MINUS, 2, "-", null), new Token(NUMBER, 2, "1", 1.0),
            new Token(SEMICOLON, 2, ";", null),
            new Token(PRINT, 4, "print", null), new Token(LEFT_PAREN, 4, "(", null),
            new Token(IDENTIFIER, 4, "p1", null), new Token(STAR, 4, "*", null),
            new Token(NUMBER, 4, "2", 2.0),
            new Token(PLUS, 4, "+", null), new Token(IDENTIFIER, 4, "p2", null),
            new Token(STAR, 4, "*", null),
            new Token(NUMBER, 4, "4", 4.0), new Token(RIGHT_PAREN, 4, ")", null),
            new Token(SLASH, 4, "/", null),
            new Token(NUMBER, 4, "2", 2.0), new Token(SEMICOLON, 4, ";", null),
            new Token(EOF, 4, null, null)));
    parsedCode.put("pluggable-calculator",
        List.of(new Token(FUNC, 1, "func", null), new Token(IDENTIFIER, 1, "calculate", null),
            new Token(LEFT_PAREN, 1, "(", null), new Token(IDENTIFIER, 1, "n1", null),
            new Token(COMMA, 1, ",", null),
            new Token(IDENTIFIER, 1, "n2", null), new Token(COMMA, 1, ",", null),
            new Token(IDENTIFIER, 1, "operation", null), new Token(RIGHT_PAREN, 1, ")", null),
            new Token(LEFT_BRACE, 1, "{", null), new Token(FUNC, 2, "func", null),
            new Token(IDENTIFIER, 2, "log", null), new Token(LEFT_PAREN, 2, "(", null),
            new Token(RIGHT_PAREN, 2, ")", null), new Token(LEFT_BRACE, 2, "{", null),
            new Token(PRINT, 3, "print", null),
            new Token(STRING, 3, "\"Performing the operation!\"", "Performing the operation!"),
            new Token(SEMICOLON, 3, ";", null), new Token(RIGHT_BRACE, 4, "}", null),
            new Token(IDENTIFIER, 6, "log", null), new Token(LEFT_PAREN, 6, "(", null),
            new Token(RIGHT_PAREN, 6, ")", null), new Token(SEMICOLON, 6, ";", null),
            new Token(RETURN, 7, "return", null), new Token(IDENTIFIER, 7, "operation", null),
            new Token(LEFT_PAREN, 7, "(", null), new Token(IDENTIFIER, 7, "n1", null),
            new Token(COMMA, 7, ",", null),
            new Token(IDENTIFIER, 7, "n2", null), new Token(RIGHT_PAREN, 7, ")", null),
            new Token(SEMICOLON, 7, ";", null), new Token(RIGHT_BRACE, 8, "}", null),
            new Token(FUNC, 10, "func", null),
            new Token(IDENTIFIER, 10, "addition", null), new Token(LEFT_PAREN, 10, "(", null),
            new Token(IDENTIFIER, 10, "n1", null), new Token(COMMA, 10, ",", null),
            new Token(IDENTIFIER, 10, "n2", null), new Token(RIGHT_PAREN, 10, ")", null),
            new Token(LEFT_BRACE, 10, "{", null), new Token(RETURN, 11, "return", null),
            new Token(IDENTIFIER, 11, "n1", null), new Token(PLUS, 11, "+", null),
            new Token(IDENTIFIER, 11, "n2", null), new Token(SEMICOLON, 11, ";", null),
            new Token(RIGHT_BRACE, 12, "}", null), new Token(VAR, 14, "var", null),
            new Token(IDENTIFIER, 14, "num", null), new Token(EQUAL, 14, "=", null),
            new Token(IDENTIFIER, 14, "calculate", null), new Token(LEFT_PAREN, 14, "(", null),
            new Token(NUMBER, 14, "5", 5.0), new Token(COMMA, 14, ",", null),
            new Token(NUMBER, 14, "2", 2.0),
            new Token(COMMA, 14, ",", null), new Token(IDENTIFIER, 14, "addition", null),
            new Token(RIGHT_PAREN, 14, ")", null), new Token(SEMICOLON, 14, ";", null),
            new Token(PRINT, 15, "print", null), new Token(IDENTIFIER, 15, "num", null),
            new Token(SEMICOLON, 15, ";", null), new Token(EOF, 15, null, null)));
    parsedCode.put("pow-calculator",
        List.of(new Token(FUNC, 1, "func", null), new Token(IDENTIFIER, 1, "get_pow", null),
            new Token(LEFT_PAREN, 1, "(", null), new Token(VAR, 1, "var", null),
            new Token(IDENTIFIER, 1, "num", null),
            new Token(COMMA, 1, ",", null), new Token(VAR, 1, "var", null),
            new Token(IDENTIFIER, 1, "pow", null),
            new Token(RIGHT_PAREN, 1, ")", null), new Token(LEFT_BRACE, 1, "{", null),
            new Token(VAR, 2, "var", null),
            new Token(IDENTIFIER, 2, "result", null), new Token(EQUAL, 2, "=", null),
            new Token(NUMBER, 2, "1", 1.0),
            new Token(SEMICOLON, 2, ";", null), new Token(FOR, 3, "for", null),
            new Token(LEFT_PAREN, 3, "(", null),
            new Token(VAR, 3, "var", null), new Token(IDENTIFIER, 3, "idx", null),
            new Token(EQUAL, 3, "=", null),
            new Token(NUMBER, 3, "0", 0.0), new Token(SEMICOLON, 3, ";", null),
            new Token(IDENTIFIER, 3, "idx", null),
            new Token(LESS, 3, "<", null), new Token(IDENTIFIER, 3, "pow", null),
            new Token(SEMICOLON, 3, ";", null),
            new Token(IDENTIFIER, 3, "idx", null), new Token(PLUS, 3, "+", null),
            new Token(PLUS, 3, "+", null),
            new Token(RIGHT_PAREN, 3, ")", null), new Token(LEFT_BRACE, 3, "{", null),
            new Token(IDENTIFIER, 4, "result", null), new Token(EQUAL, 4, "=", null),
            new Token(IDENTIFIER, 4, "result", null), new Token(STAR, 4, "*", null),
            new Token(IDENTIFIER, 4, "num", null), new Token(SEMICOLON, 4, ";", null),
            new Token(RIGHT_BRACE, 5, "}", null), new Token(RETURN, 6, "return", null),
            new Token(IDENTIFIER, 6, "result", null), new Token(SEMICOLON, 6, ";", null),
            new Token(RIGHT_BRACE, 7, "}", null), new Token(PRINT, 9, "print", null),
            new Token(IDENTIFIER, 9, "get_pow", null), new Token(LEFT_PAREN, 9, "(", null),
            new Token(NUMBER, 9, "4", 4.0), new Token(COMMA, 9, ",", null),
            new Token(NUMBER, 9, "3", 3.0),
            new Token(RIGHT_PAREN, 9, ")", null), new Token(SEMICOLON, 9, ";", null),
            new Token(EOF, 9, null, null)));
    parsedCode.put("print-my-name",
        List.of(new Token(FUNC, 1, "func", null),
            new Token(IDENTIFIER, 1, "create_my_greeting", null),
            new Token(LEFT_PAREN, 1, "(", null), new Token(IDENTIFIER, 1, "name", null),
            new Token(RIGHT_PAREN, 1, ")", null), new Token(LEFT_BRACE, 1, "{", null),
            new Token(IF, 2, "if", null),
            new Token(LEFT_PAREN, 2, "(", null), new Token(IDENTIFIER, 2, "name", null),
            new Token(RIGHT_PAREN, 2, ")", null), new Token(LEFT_BRACE, 2, "{", null),
            new Token(RETURN, 3, "return", null),
            new Token(STRING, 3, "\"hello theree!! my name is \"", "hello theree!! my name is "),
            new Token(PLUS, 3, "+", null), new Token(IDENTIFIER, 3, "name", null),
            new Token(SEMICOLON, 3, ";", null),
            new Token(RIGHT_BRACE, 4, "}", null), new Token(ELSE, 5, "else", null),
            new Token(LEFT_BRACE, 5, "{", null),
            new Token(RETURN, 6, "return", null),
            new Token(STRING, 6, "\"helloo stranger, what you are doing today ;)\"",
                "helloo stranger, what you are doing today ;)"), new Token(SEMICOLON, 6, ";", null),
            new Token(RIGHT_BRACE, 7, "}", null), new Token(RIGHT_BRACE, 8, "}", null),
            new Token(VAR, 10, "var", null),
            new Token(IDENTIFIER, 10, "name", null), new Token(EQUAL, 10, "=", null),
            new Token(STRING, 10, "\"hddt\"", "hddt"), new Token(SEMICOLON, 10, ";", null),
            new Token(IDENTIFIER, 13, "create_my_greeting", null),
            new Token(LEFT_PAREN, 13, "(", null),
            new Token(IDENTIFIER, 13, "name", null), new Token(RIGHT_PAREN, 13, ")", null),
            new Token(SEMICOLON, 13, ";", null), new Token(EOF, 16, null, null)));
    parsedCode.put("rpg-sim",
        List.of(new Token(CLASS, 1, "class", null), new Token(IDENTIFIER, 1, "Character", null),
            new Token(LEFT_BRACE, 1, "{", null), new Token(IDENTIFIER, 2, "init", null),
            new Token(LEFT_PAREN, 2, "(", null), new Token(IDENTIFIER, 2, "name", null),
            new Token(COMMA, 2, ",", null),
            new Token(IDENTIFIER, 2, "initial_health", null), new Token(COMMA, 2, ",", null),
            new Token(IDENTIFIER, 2, "attack_damage", null), new Token(RIGHT_PAREN, 2, ")", null),
            new Token(LEFT_BRACE, 2, "{", null), new Token(THIS, 3, "this", null),
            new Token(DOT, 3, ".", null),
            new Token(IDENTIFIER, 3, "name", null), new Token(EQUAL, 3, "=", null),
            new Token(IDENTIFIER, 3, "name", null),
            new Token(SEMICOLON, 3, ";", null), new Token(THIS, 4, "this", null),
            new Token(DOT, 4, ".", null),
            new Token(IDENTIFIER, 4, "health", null), new Token(EQUAL, 4, "=", null),
            new Token(IDENTIFIER, 4, "initial_health", null), new Token(SEMICOLON, 4, ";", null),
            new Token(THIS, 5, "this", null), new Token(DOT, 5, ".", null),
            new Token(IDENTIFIER, 5, "attack_damage", null),
            new Token(EQUAL, 5, "=", null), new Token(IDENTIFIER, 5, "attack_damage", null),
            new Token(SEMICOLON, 5, ";", null), new Token(RIGHT_BRACE, 6, "}", null),
            new Token(IDENTIFIER, 8, "is_dead", null), new Token(LEFT_PAREN, 8, "(", null),
            new Token(RIGHT_PAREN, 8, ")", null), new Token(LEFT_BRACE, 8, "{", null),
            new Token(RETURN, 9, "return", null),
            new Token(IDENTIFIER, 9, "health", null), new Token(LESS_EQUAL, 9, "<=", null),
            new Token(NUMBER, 9, "0", 0.0),
            new Token(SEMICOLON, 9, ";", null), new Token(RIGHT_BRACE, 10, "}", null),
            new Token(IDENTIFIER, 12, "damage", null), new Token(LEFT_PAREN, 12, "(", null),
            new Token(IDENTIFIER, 12, "amount", null), new Token(RIGHT_PAREN, 12, ")", null),
            new Token(LEFT_BRACE, 12, "{", null), new Token(IF, 13, "if", null),
            new Token(LEFT_PAREN, 13, "(", null),
            new Token(IDENTIFIER, 13, "is_dead", null), new Token(LEFT_PAREN, 13, "(", null),
            new Token(RIGHT_PAREN, 13, ")", null), new Token(RIGHT_PAREN, 13, ")", null),
            new Token(LEFT_BRACE, 13, "{", null), new Token(THIS, 14, "this", null),
            new Token(DOT, 14, ".", null),
            new Token(IDENTIFIER, 14, "health", null), new Token(EQUAL, 14, "=", null),
            new Token(IDENTIFIER, 14, "health", null), new Token(MINUS, 14, "-", null),
            new Token(IDENTIFIER, 14, "amount", null), new Token(SEMICOLON, 14, ";", null),
            new Token(RIGHT_BRACE, 15, "}", null), new Token(RIGHT_BRACE, 16, "}", null),
            new Token(IDENTIFIER, 18, "attack", null), new Token(LEFT_PAREN, 18, "(", null),
            new Token(IDENTIFIER, 18, "other_character", null),
            new Token(RIGHT_PAREN, 18, ")", null),
            new Token(LEFT_BRACE, 18, "{", null),
            new Token(IDENTIFIER, 19, "other_character", null),
            new Token(DOT, 19, ".", null), new Token(IDENTIFIER, 19, "damage", null),
            new Token(LEFT_PAREN, 19, "(", null),
            new Token(THIS, 19, "this", null), new Token(DOT, 19, ".", null),
            new Token(IDENTIFIER, 19, "attack_damage", null), new Token(RIGHT_PAREN, 19, ")", null),
            new Token(SEMICOLON, 19, ";", null), new Token(RIGHT_BRACE, 20, "}", null),
            new Token(RIGHT_BRACE, 21, "}", null), new Token(CLASS, 23, "class", null),
            new Token(IDENTIFIER, 23, "Hero", null), new Token(LESS, 23, "<", null),
            new Token(IDENTIFIER, 23, "Character", null), new Token(LEFT_BRACE, 23, "{", null),
            new Token(IDENTIFIER, 24, "init", null), new Token(LEFT_PAREN, 24, "(", null),
            new Token(RIGHT_PAREN, 24, ")", null), new Token(LEFT_BRACE, 24, "{", null),
            new Token(SUPER, 25, "super", null), new Token(DOT, 25, ".", null),
            new Token(IDENTIFIER, 25, "init", null),
            new Token(LEFT_PAREN, 25, "(", null), new Token(STRING, 25, "\"Hero\"", "Hero"),
            new Token(COMMA, 25, ",", null), new Token(NUMBER, 25, "100", 100.0),
            new Token(COMMA, 25, ",", null),
            new Token(NUMBER, 25, "20", 20.0), new Token(RIGHT_PAREN, 25, ")", null),
            new Token(SEMICOLON, 25, ";", null),
            new Token(RIGHT_BRACE, 26, "}", null), new Token(RIGHT_BRACE, 27, "}", null),
            new Token(CLASS, 29, "class", null), new Token(IDENTIFIER, 29, "Goblin", null),
            new Token(LESS, 29, "<", null),
            new Token(IDENTIFIER, 29, "Character", null), new Token(LEFT_BRACE, 29, "{", null),
            new Token(IDENTIFIER, 30, "init", null), new Token(LEFT_PAREN, 30, "(", null),
            new Token(RIGHT_PAREN, 30, ")", null), new Token(LEFT_BRACE, 30, "{", null),
            new Token(SUPER, 31, "super", null), new Token(DOT, 31, ".", null),
            new Token(IDENTIFIER, 31, "init", null),
            new Token(LEFT_PAREN, 31, "(", null), new Token(STRING, 31, "\"Goblin\"", "Goblin"),
            new Token(COMMA, 31, ",", null), new Token(NUMBER, 31, "40", 40.0),
            new Token(COMMA, 31, ",", null),
            new Token(NUMBER, 31, "15", 15.0), new Token(RIGHT_PAREN, 31, ")", null),
            new Token(SEMICOLON, 31, ";", null),
            new Token(RIGHT_BRACE, 32, "}", null), new Token(RIGHT_BRACE, 33, "}", null),
            new Token(VAR, 36, "var", null),
            new Token(IDENTIFIER, 36, "player", null), new Token(EQUAL, 36, "=", null),
            new Token(NEW, 36, "new", null),
            new Token(IDENTIFIER, 36, "Hero", null), new Token(LEFT_PAREN, 36, "(", null),
            new Token(RIGHT_PAREN, 36, ")", null), new Token(SEMICOLON, 36, ";", null),
            new Token(VAR, 37, "var", null),
            new Token(IDENTIFIER, 37, "enemy", null), new Token(EQUAL, 37, "=", null),
            new Token(NEW, 37, "new", null),
            new Token(IDENTIFIER, 37, "Goblin", null), new Token(LEFT_PAREN, 37, "(", null),
            new Token(RIGHT_PAREN, 37, ")", null), new Token(SEMICOLON, 37, ";", null),
            new Token(WHILE, 39, "while", null),
            new Token(LEFT_PAREN, 39, "(", null), new Token(BANG, 39, "!", null),
            new Token(IDENTIFIER, 39, "player", null),
            new Token(DOT, 39, ".", null), new Token(IDENTIFIER, 39, "is_dead", null),
            new Token(LEFT_PAREN, 39, "(", null),
            new Token(RIGHT_PAREN, 39, ")", null), new Token(AND, 39, "and", null),
            new Token(BANG, 39, "!", null),
            new Token(IDENTIFIER, 39, "enemy", null), new Token(DOT, 39, ".", null),
            new Token(IDENTIFIER, 39, "is_dead", null), new Token(LEFT_PAREN, 39, "(", null),
            new Token(RIGHT_PAREN, 39, ")", null), new Token(RIGHT_PAREN, 39, ")", null),
            new Token(LEFT_BRACE, 39, "{", null), new Token(IDENTIFIER, 40, "player", null),
            new Token(DOT, 40, ".", null),
            new Token(IDENTIFIER, 40, "attack", null), new Token(LEFT_PAREN, 40, "(", null),
            new Token(IDENTIFIER, 40, "enemy", null), new Token(RIGHT_PAREN, 40, ")", null),
            new Token(SEMICOLON, 40, ";", null), new Token(IDENTIFIER, 41, "enemy", null),
            new Token(DOT, 41, ".", null),
            new Token(IDENTIFIER, 41, "attack", null), new Token(LEFT_PAREN, 41, "(", null),
            new Token(IDENTIFIER, 41, "player", null), new Token(RIGHT_PAREN, 41, ")", null),
            new Token(SEMICOLON, 41, ";", null), new Token(RIGHT_BRACE, 42, "}", null),
            new Token(PRINT, 44, "print", null),
            new Token(STRING, 44, "\"Player alive: \"", "Player alive: "),
            new Token(PLUS, 44, "+", null),
            new Token(IDENTIFIER, 44, "player", null), new Token(DOT, 44, ".", null),
            new Token(IDENTIFIER, 44, "is_dead", null), new Token(LEFT_PAREN, 44, "(", null),
            new Token(RIGHT_PAREN, 44, ")", null), new Token(SEMICOLON, 44, ";", null),
            new Token(PRINT, 45, "print", null),
            new Token(STRING, 45, "\"Enemy alive: \"", "Enemy alive: "),
            new Token(PLUS, 45, "+", null),
            new Token(IDENTIFIER, 45, "enemy", null), new Token(DOT, 45, ".", null),
            new Token(IDENTIFIER, 45, "is_dead", null), new Token(LEFT_PAREN, 45, "(", null),
            new Token(RIGHT_PAREN, 45, ")", null), new Token(SEMICOLON, 45, ";", null),
            new Token(EOF, 45, null, null)));
  }

  public static String getCode(String fileName) {
    try {
      Path path = Paths.get(requireNonNull(
          SampleLoxCode.class.getClassLoader()
              .getResource(String.format("sample-lox-code/%s.lox", fileName))).toURI());
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format("File %s.lox doesn't exist", fileName), e);
    }
  }

  public static List<Token> getTokens(String fileName) {
    return requireNonNull(parsedCode.get(fileName));
  }

  public static List<String> getSampleCodeList() {
    return new ArrayList<>(parsedCode.keySet());
  }
}

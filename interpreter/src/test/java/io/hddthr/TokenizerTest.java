package io.hddthr;

import static io.hddthr.model.TokenType.EOF;
import static io.hddthr.model.TokenType.IDENTIFIER;
import static io.hddthr.model.TokenType.NUMBER;
import static io.hddthr.model.TokenType.STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.hddthr.model.Token;
import io.hddthr.model.TokenType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TokenizerTest {

  private Tokenizer tokenizer;

  static Stream<Arguments> streamFilenameArguments() {
    return SampleLoxCode.getSampleCodeList().stream().map(Arguments::of);
  }

  public static Stream<Arguments> streamValidLexemesArguments() {
    return Stream.of(Arguments.of("(", TokenType.LEFT_PAREN),
        Arguments.of(")", TokenType.RIGHT_PAREN),
        Arguments.of("{", TokenType.LEFT_BRACE), Arguments.of("}", TokenType.RIGHT_BRACE),
        Arguments.of(".", TokenType.DOT), Arguments.of("+", TokenType.PLUS),
        Arguments.of("-", TokenType.MINUS),
        Arguments.of("*", TokenType.STAR), Arguments.of("/", TokenType.SLASH),
        Arguments.of(",", TokenType.COMMA),
        Arguments.of(";", TokenType.SEMICOLON), Arguments.of("=", TokenType.EQUAL),
        Arguments.of("==", TokenType.EQUAL_EQUAL), Arguments.of(">", TokenType.GREATER),
        Arguments.of(">=", TokenType.GREATER_EQUAL), Arguments.of("<", TokenType.LESS),
        Arguments.of("<=", TokenType.LESS_EQUAL), Arguments.of("!", TokenType.BANG),
        Arguments.of("!=", TokenType.BANG_EQUAL), Arguments.of("and", TokenType.AND),
        Arguments.of("or", TokenType.OR),
        Arguments.of("var", TokenType.VAR), Arguments.of("new", TokenType.NEW),
        Arguments.of("class", TokenType.CLASS),
        Arguments.of("func", TokenType.FUNC), Arguments.of("if", TokenType.IF),
        Arguments.of("for", TokenType.FOR),
        Arguments.of("while", TokenType.WHILE), Arguments.of("return", TokenType.RETURN),
        Arguments.of("super", TokenType.SUPER), Arguments.of("print", TokenType.PRINT),
        Arguments.of("true", TokenType.TRUE), Arguments.of("false", TokenType.FALSE),
        Arguments.of("nil", TokenType.NIL), Arguments.of("else", TokenType.ELSE),
        Arguments.of("this", TokenType.THIS));
  }

  @BeforeEach
  void init() {
    tokenizer = new Tokenizer();
  }

  @ParameterizedTest
  @NullAndEmptySource
  void givenEmptyOrNullInputWhenTokenizeThenReturnEOFToken(String input) {
    assertIterableEquals(List.of(new Token(EOF, 1)), tokenizer.tokenize(input));
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
      '#', 'Illegal character at line 1, character 1'
      '&', 'Illegal character at line 1, character 1'
      '@', 'Illegal character at line 1, character 1'
      """)
  void givenInvalidLexemesWhenTokenizeThenThrowDetailedException(String input,
      String expectedMessage) {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> tokenizer.tokenize(input));
    assertEquals(expectedMessage, ex.getMessage());
  }

  @ParameterizedTest
  @MethodSource("streamValidLexemesArguments")
  void givenValidLexemesWhenTokenizeThenReturnList(String input, TokenType type) {
    assertIterableEquals(List.of(new Token(type, 1, input, null), new Token(EOF, 1)),
        tokenizer.tokenize(input));
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
      '" helloo "" ', 'Unterminated string at line 1, character 11'
      '"another example
      "', 'Unterminated string at line 1, character 1'
      '"apple"
         sdkkf"', 'Unterminated string at line 2, character 9'
      """)
  void givenInvalidStringWhenTokenizeThenThrowDetailedException(String input,
      String expectedMessage) {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> tokenizer.tokenize(input));
    assertEquals(expectedMessage, ex.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"I'm a valid string!!", "brunch", "2q3523", "@$@!@!FF"})
  void givenValidStringWhenTokenizeThenTokenize(String input) {
    String enclosedString = String.format("\"%s\"", input);
    assertIterableEquals(List.of(new Token(STRING, 1, enclosedString, input), new Token(EOF, 1)),
        tokenizer.tokenize(enclosedString));
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
      '3.121.', 'Invalid number at line 1, character 1'
      '21.', 'Invalid number at line 1, character 1'
      '32 333df', 'Invalid number at line 1, character 4'
      '.0421', 'Invalid number at line 1, character 2'
      '.0.01', 'Invalid number at line 1, character 2'
      """)
  void givenInvalidNumberWhenTokenizeThenThrowDetailedException(String input,
      String expectedMessage) {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> tokenizer.tokenize(input));
    assertEquals(expectedMessage, ex.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"51.2", "0.005"})
  void givenValidDoubleWhenTokenizeThenReturnList(String input) {
    assertIterableEquals(
        List.of(new Token(NUMBER, 1, input, Double.valueOf(input)), new Token(EOF, 1)),
        tokenizer.tokenize(input));
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
      'hdddt@gmail', 'Illegal character at line 1, character 6'
      '7omos', 'Invalid number at line 1, character 1'
      """)
  void givenInvalidIdentifierLexemesWhenTokenizeThenThrowDetailedException(String input,
      String expectedMessage) {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
        () -> tokenizer.tokenize(input));
    assertEquals(expectedMessage, ex.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"APPLE", "student15", "he_her", "_the_he_man"})
  void givenIdentifierLexemeWhenTokenizeThenReturnList(String input) {
    assertIterableEquals(List.of(new Token(IDENTIFIER, 1, input, null), new Token(EOF, 1)),
        tokenizer.tokenize(input));
  }

  @Test
  void givenCommentWhenTokenizeThenReturnList() {
    assertIterableEquals(List.of(new Token(EOF, 1)), tokenizer.tokenize("// This is a test"));
  }

  @ParameterizedTest
  @MethodSource("streamFilenameArguments")
  void givenComplexSamplesWhenTokenizeThenReturnList(String filename) {
    String input = SampleLoxCode.getCode(filename);
    assertIterableEquals(SampleLoxCode.getTokens(filename), tokenizer.tokenize(input),
        String.format("Sample code %s tokenized incorrectly", filename));
  }
}

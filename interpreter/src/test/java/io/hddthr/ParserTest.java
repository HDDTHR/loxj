package io.hddthr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import io.hddthr.model.Token;

public class ParserTest {
  @ParameterizedTest
  @NullAndEmptySource
  void givenEmptyOrNullTokensWhenParseThenThrowException(List<String> tokens) {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()-> Parser.parse(tokens));
    assertEquals("Empty token list supplied to parser", ex.getMessage());
  }

  @Test
  void givenInvalidAdditionExpressionsWhenParseThenReturnException() {

  }
}

package io.hddthr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Token {
  private final TokenType type;
  private final int line;
  private String lexeme;
  private Object literal;
}

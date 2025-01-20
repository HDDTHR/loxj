package io.hddthr;

import java.util.List;

public class Parser {
  public static void parse(List<String> tokens) {
    if (tokens == null || tokens.isEmpty()) {
      throw new IllegalArgumentException("Empty token list supplied to parser");
    }

  }
}

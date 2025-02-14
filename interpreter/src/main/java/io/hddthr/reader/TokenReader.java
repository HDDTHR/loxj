package io.hddthr.reader;

import io.hddthr.model.Token;
import io.hddthr.model.TokenType;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenReader {

  private final List<Token> tokens;
  private int index = 0;

  public boolean match(TokenType... types) {
    boolean match = index + 1 < tokens.size() && Arrays.stream(types)
        .anyMatch(type -> tokens.get(index).getType() == type);
    if (match) {
      advance();
    }
    return match;
  }

  public void advance() {
    index++;
  }

  public Token previous() {
    if (index - 1 < 0) {
      return null;
    }
    return tokens.get(index - 1);
  }

  public Token getCurrent() {
    return tokens.get(index);
  }

  public boolean isAtEnd() {
    return index + 1 >= tokens.size();
  }

  public Token peek(int idx) {
    return index + idx < tokens.size() ? tokens.get(index + idx) : null;
  }
}

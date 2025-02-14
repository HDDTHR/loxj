package io.hddthr.reader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringReader {

  private final String input;
  private int previousCharacters = 0;
  private int index = 0;
  private int start = 0;
  @Getter
  private int line = 1;

  public boolean reachedEOF() {
    return index >= input.length();
  }

  public char getCurrent() {
    return input.charAt(index);
  }

  public char peekBack(int idx) {
    if (index - idx > 0) {
      return '\0';
    }
    return input.charAt(index - idx);
  }

  public String getSubstringToIndex() {
    if (index + 1 >= input.length()) {
      return input.substring(start);
    }
    return input.substring(start, index + 1);
  }

  public void addLine() {
    line++;
    previousCharacters = index + 1;
  }

  public int getStartIndexRelativeToLine() {
    return start - previousCharacters + 1;
  }

  public int getIndexRelativeToLine() {
    return index - previousCharacters + 1;
  }

  public boolean match(char c) {
    boolean match = index + 1 < input.length() && (peek(1) == c);
    if (match) {
      advance();
    }
    return match;
  }

  public char peek(int idx) {
    if (index + idx >= input.length()) {
      return '\0';
    }
    return input.charAt(index + idx);
  }

  public void advance() {
    ++index;
  }

  public void setStartingPosition() {
    start = index;
  }
}

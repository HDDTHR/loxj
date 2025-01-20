package io.hddthr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reader {
  private final String input;
  private int index = 0;
  @Getter
  private int line = 1;
  private int previousCharacters = 0;
  private int start = 0;

  public boolean reachedEOF() {
    return index >= input.length();
  }

  char getCurrentCharacter() {
    return input.charAt(index);
  }

  public void advance() {
    ++index;
  }

  public char peekBack() {
    if (index == 0) return '\0';
    return input.charAt(index - 1);
  }

  public String getSubstringToIndex() {
    if (index + 1 >= input.length())
      return input.substring(start);
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

  public boolean nextMatches(char c) {
    return index + 1 < input.length() && (peek(1) == c);
  }

  public char peek(int val) {
    if (index + val >= input.length()) return '\0';
    return input.charAt(index + val);
  }

  public void resetStartPosition() {
    start = index;
  }
}

package io.hddthr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.hddthr.model.Expr;
import io.hddthr.model.Token;
import io.hddthr.visitor.PrintVisitor;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ParserTest {

  private Parser parser;

  static Stream<Arguments> streamFilenameArguments() {
    return SampleLoxCode.getSampleCodeList().stream().map(Arguments::of);
  }

  @BeforeEach
  void setUp() {
    this.parser = new Parser();
  }

  @ParameterizedTest
  @MethodSource("streamFilenameArguments")
  void givenComplexSamplesTokensWhenParseThenReturnAST(String filename) {
    List<Token> input = SampleLoxCode.getTokens(filename);
    PrintVisitor visitor = new PrintVisitor();
    Expr expression = parser.parse(input);
    assertEquals("", expression.accept(visitor));
  }
}

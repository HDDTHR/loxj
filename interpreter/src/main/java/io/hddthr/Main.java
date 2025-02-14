package io.hddthr;

import io.hddthr.model.Stmt;
import io.hddthr.model.Token;
import io.hddthr.visitor.Interpreter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

  private static final Tokenizer tokenizer = new Tokenizer();
  private static final Parser parser = new Parser();
  private static final Interpreter interpreter = new Interpreter();

  public static void main(String[] args) {
    Options options = new Options();
    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, args, true);
      String[] positionalArgs = cmd.getArgs();
      if (positionalArgs.length == 0) {
        runPrompt();
      }
      run(getFileContent(positionalArgs[0]));
    } catch (ParseException | RuntimeException | IOException e) {
      System.err.println(e.getMessage());
      formatter.printHelp("java jlox <filename>", options);
      System.exit(1);
    }
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (; ; ) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null) {
        break;
      }
      run(line);
    }
  }

  private static void run(String source) {
    List<Token> tokens = tokenizer.tokenize(source);
    List<Stmt> statements = parser.parse(tokens);
    statements.forEach(stmt -> stmt.accept(interpreter));
  }

  private static String getFileContent(String filename) {
    try {
      return Files.readString(Path.of(filename), Charset.defaultCharset());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

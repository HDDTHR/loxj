package io.hddthr;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

  public static void main(String[] args) {
    Options options = new Options();
    HelpFormatter formatter = new HelpFormatter();
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, args, true);
      String[] positionalArgs = cmd.getArgs();
      if (positionalArgs.length == 0) {
        throw new ParseException("Filename is required.");
      }
      String filename = positionalArgs[0];
      System.out.println(new Tokenizer().tokenize(getFileContent(filename)));
    } catch (ParseException | RuntimeException e) {
      System.err.println(e.getMessage());
      formatter.printHelp("java ilox <filename>", options);
      System.exit(1);
    }
  }

  private static String getFileContent(String filename) {
    try {
      return Files.readString(Path.of(filename), Charset.defaultCharset());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

package io.hddthr;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class ExprGenerator {

  private static VelocityContext context;
  private static VelocityEngine velocityEngine;

  public static void main(String[] args) {
    velocityEngine = initEngine();
    context = initContext(args[0]);

    try {
      generateExpressionJavaFile(Path.of(args[1], "model"), "Expr.java");
      generateVisitorJavaFile(Path.of(args[1], "visitor"), "Visitor.java");
    } catch (IOException e) {
      System.err.printf("Generation Exception: %s%n", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private static VelocityEngine initEngine() {
    Properties props = new Properties();
    props.setProperty("resource.loader", "class");
    props.setProperty("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.init(props);
    return velocityEngine;
  }

  private static VelocityContext initContext(String schemaFilepath) {
    VelocityContext context = new VelocityContext();
    String content;
    try {
      content = Files.readString(Path.of(schemaFilepath), Charset.defaultCharset());
    } catch (IOException e) {
      System.err.printf("initContext Exception: %s%n", e.getMessage());
      throw new RuntimeException(e);
    }
    List<Expression> expressions = new ArrayList<>();
    for (String line : content.split("\n")) {
      String[] parts = line.split(":");
      expressions.add(new Expression(parts[0].strip(), Arrays.stream(parts[1].split(",")).map(String::trim).toList()));
    }
    context.put("expressions", expressions);
    context.put("StringUtils", new StringUtils());
    return context;
  }

  private static void generateExpressionJavaFile(Path path, String name) throws IOException {
    Template template = velocityEngine.getTemplate("Expr.vm");
    Files.createDirectories(path);
    FileWriter writer = new FileWriter(path.resolve(Path.of(name)).toString(), Charset.defaultCharset(), false);
    template.merge(context, writer);
    writer.close();
  }

  private static void generateVisitorJavaFile(Path path, String name) throws IOException {
    Template template = velocityEngine.getTemplate("Visitor.vm");
    Files.createDirectories(path);
    FileWriter writer = new FileWriter(path.resolve(Path.of(name)).toString(), Charset.defaultCharset(), false);
    template.merge(context, writer);
    writer.close();
  }
  public record Expression(String name, List<String> fields) {
  }
}

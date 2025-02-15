package io.hddthr.exception;

import io.hddthr.model.ParsingError;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class ParserException extends RuntimeException {
  private final List<ParsingError> errors;

  public ParserException(List<ParsingError> errors) {
    this.errors = errors;
  }
}

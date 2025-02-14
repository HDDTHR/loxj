package io.hddthr.model;

public record ParsingError(Token token, String message) { }

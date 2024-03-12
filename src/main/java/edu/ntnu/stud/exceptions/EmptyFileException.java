package edu.ntnu.stud.exceptions;

public class EmptyFileException extends RuntimeException {
  public EmptyFileException() {
    super("The file is empty and cannot be processed.");
  }

  public EmptyFileException(String message) {
    super(message);
  }
}

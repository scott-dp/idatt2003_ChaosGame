package edu.ntnu.stud.models.exceptions;

/**
 * Thrown when the {@link edu.ntnu.stud.models.chaosgamehandling.ChaosGameFileHandler}
 * tries to read from an empty file.
 */
public class EmptyFileException extends RuntimeException {

  /**
   * Constructor for EmptyFileException with default message.
   */
  public EmptyFileException() {
    super("The file is empty and cannot be processed.");
  }

  /**
   * Constructor for EmptyFileException with a given message to add to the exception.
   *
   * @param message the message to be added to the exception
   */
  public EmptyFileException(String message) {
    super(message);
  }
}

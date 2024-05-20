package edu.ntnu.stud.views.fileviews;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * A view that allows the user to load a fractal from a file.
 */
public class LoadFileView {
  private final FileChooser fileChooser;
  private final Stage stage;

  /**
   * Constructs a LoadFileView with a FileChooser and a Stage.
   */
  public LoadFileView() {
    fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    fileChooser.setTitle("Load a fractal from a file");
    stage = new Stage();
  }

  /**
   * Shows the file chooser dialog and returns the chosen file path.
   *
   * @return The chosen file path.
   */
  public String getChosenFilePath() {
    return fileChooser.showOpenDialog(stage).toString();
  }
}

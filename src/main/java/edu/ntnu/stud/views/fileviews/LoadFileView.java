package edu.ntnu.stud.views.fileviews;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadFileView {
  private final FileChooser fileChooser;
  private final Stage stage;

  public LoadFileView() {
    fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    fileChooser.setTitle("Load a fractal from a file");
    stage = new Stage();
  }

  public String getChosenFilePath() {
    return fileChooser.showOpenDialog(stage).toString();
  }
}

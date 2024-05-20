package edu.ntnu.stud.views.fileviews;

import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * A view class for saving the current fractal being shown to a file in a directory that the user
 * chooses.
 */
public class SaveFileView {
  private final DirectoryChooser directoryChooser;
  private final Stage stage;

  /**
   * Constructs a SaveFileView object and initializes the DirectoryChooser and Stage objects.
   */
  public SaveFileView() {
    directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Save current fractal to a directory");
    stage = new Stage();
  }

  /**
   * Shows a dialog for the user to choose a directory to save the fractal in.
   *
   * @return The path of the chosen directory.
   */
  public String getChosenDirectory() {
    return directoryChooser.showDialog(stage).toString();
  }

  /**
   * Shows a dialog for the user to enter a filename to save the fractal in.
   *
   * @return The filename entered by the user.
   */
  public String getFileNameFromUser() {
    Stage setFileNameStage = new Stage();
    setFileNameStage.setTitle("Enter filename to save fractal in");
    TextField fileNameField = new TextField("File name");
    Button chooseDirectoryButton = new Button("Choose directory to save file in");
    AtomicReference<String> fileName = new AtomicReference<>("");
    chooseDirectoryButton.setOnAction(e -> {
      fileName.set(fileNameField.getText());
      setFileNameStage.close();
    });
    HBox layout = new HBox(10);
    layout.getChildren().addAll(fileNameField, chooseDirectoryButton);
    setFileNameStage.setScene(new Scene(layout));
    setFileNameStage.showAndWait();
    return fileName.toString();
  }
}

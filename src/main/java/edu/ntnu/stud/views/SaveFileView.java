package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class SaveFileView {
  private final DirectoryChooser directoryChooser;
  private final Stage stage;

  public SaveFileView() {
    directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Save current fractal to a directory");
    stage = new Stage();
  }

  public String getChosenDirectory() {
    return directoryChooser.showDialog(stage).toString();
  }

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

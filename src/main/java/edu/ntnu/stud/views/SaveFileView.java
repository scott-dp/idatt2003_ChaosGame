package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SaveFileView {
  private final static ChaosGameController chaosGameController = AppView.getChaosGameController();
  private final DirectoryChooser directoryChooser;
  private final Stage stage;

  public SaveFileView() {
    directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Save current fractal to a directory");
    stage = new Stage();
  }

  public String getChosenDirectory() { //Can return null, make checks
    return directoryChooser.showDialog(stage).toString().concat("/TestSaveFile.txt");
  }
}

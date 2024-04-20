package edu.ntnu.stud.views;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditAffineTransformView {
  private final Stage stage;

  public EditAffineTransformView() {
    stage = new Stage();
  }

  public void showStage() {
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }
}

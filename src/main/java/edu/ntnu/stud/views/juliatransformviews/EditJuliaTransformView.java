package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.models.Complex;
import edu.ntnu.stud.models.JuliaTransform;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.juliatransformviews.AbstractJuliaTransformView;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.ArrayList;

public class EditJuliaTransformView extends AbstractJuliaTransformView {

  public EditJuliaTransformView() {
    super();
    JuliaTransform currentTransform = (JuliaTransform) chaosGameController.getChaosGame().getDescription().getTransforms().get(0);
  }


  public void showStage() {
    setScene();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }

  @Override
  public void setScene() {
    this.scene.setRoot(setMainLayout());
    setCoordsInView();
    setTransformInView((JuliaTransform) chaosGameController.getChaosGame().getDescription().getTransforms().get(0));
  }


  @Override
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    mainLayout.getChildren().addAll(createNumeralInput(),getCoordinatesInput(),createSaveButton());

    return mainLayout;
  }



  @Override
  public void saveButtonAction(ActionEvent event) {
    if (!isInputValid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    } else {
      chaosGameController.setChaosGame(createJuliaDescription());
      stage.close();
    }
  }


  public ChaosGameDescription createJuliaDescription() {
    ArrayList<Transform2D> transforms = new ArrayList<>();
    transforms.add(new JuliaTransform(createComplex(), 1));
    transforms.add(new JuliaTransform(createComplex(), -1));
    return new ChaosGameDescription(createMinCoordinates(), createMaxCoordinates(), transforms);
  }

  public Complex createComplex() {
    return new Complex(Double.parseDouble(realPartField.getText()), Double.parseDouble(imaginaryPartField.getText()));
  }

  public Vector2D createMinCoordinates() {
    return new Vector2D(Double.parseDouble(minX0.getText()), Double.parseDouble(minX1.getText()));
  }

  public Vector2D createMaxCoordinates() {
    return new Vector2D(Double.parseDouble(maxX0.getText()), Double.parseDouble(maxX1.getText()));
  }

  public void setCoordsInView() {
    minX0.setText(String.valueOf(chaosGameController.getChaosGame().getDescription().getMinCoords().getX0()));
    minX1.setText(String.valueOf(chaosGameController.getChaosGame().getDescription().getMinCoords().getX1()));
    maxX0.setText(String.valueOf(chaosGameController.getChaosGame().getDescription().getMaxCoords().getX0()));
    maxX1.setText(String.valueOf(chaosGameController.getChaosGame().getDescription().getMaxCoords().getX1()));
  }

  public void setTransformInView(JuliaTransform transform) {
    realPartField.setText(String.valueOf(transform.getConstantPoint().getRealPart()));
    imaginaryPartField.setText(String.valueOf(transform.getConstantPoint().getImaginaryPart()));
  }
}

package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;

public class EditJuliaTransformView extends AbstractJuliaTransformView {
  JuliaTransform currentTransform;
  public EditJuliaTransformView() {
    super();
    currentTransform = (JuliaTransform) chaosGameController.getChaosGame().getDescription().getTransforms().get(0);
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
    setTransformInView(currentTransform);
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
    } else {
      chaosGameController.setChaosGame(createJuliaDescription());
      stage.close();
    }
  }


  public ChaosGameDescription createJuliaDescription() {
    List<Transform2D> transforms = new ArrayList<>();
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

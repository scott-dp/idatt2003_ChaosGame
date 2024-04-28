package edu.ntnu.stud.views.affinetransformviews;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.mathematics.Matrix2x2;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditAffineTransformView extends AbstractAffineTransformView {
  int currentIndex;
  public EditAffineTransformView() {
    super();
    currentIndex = 0;
    affineTransforms = chaosGameController.getChaosGame().getDescription().getTransforms();
  }

  /**
   * Method that sets the scene of the stage.
   */
  @Override
  public void setScene() {
    scene.setRoot(setMainLayout());
    setAffineTransformScene(affineTransforms.get(0));
    setCoordsInView(chaosGameController.getChaosGame().getDescription());
  }

  public void setCoordsInView(ChaosGameDescription description) {
    minX0.setText(String.valueOf(description.getMinCoords().getX0()));
    minX1.setText(String.valueOf(description.getMinCoords().getX1()));
    maxX0.setText(String.valueOf(description.getMaxCoords().getX0()));
    maxX1.setText(String.valueOf(description.getMaxCoords().getX1()));
  }

  public void setAffineTransformScene(Transform2D transform) {
    AffineTransform2D affineTransform;
    if (transform instanceof AffineTransform2D) {
      affineTransform = (AffineTransform2D) transform;
    } else {
      return;
    }
    a00.setText(String.valueOf(affineTransform.getMatrix().getA00()));
    a01.setText(String.valueOf(affineTransform.getMatrix().getA01()));
    a10.setText(String.valueOf(affineTransform.getMatrix().getA10()));
    a11.setText(String.valueOf(affineTransform.getMatrix().getA11()));

    x0.setText(String.valueOf(affineTransform.getVector().getX0()));
    x1.setText(String.valueOf(affineTransform.getVector().getX1()));
  }

  /**
   * Method that sets the main layout of the scene.
   * The layout consists of a horizontal container that holds two vertical containers,
   * used for entering the matrix and vector of the transformation, and the min and max coordinates
   * of the fractal.
   *
   * @return VBox mainLayout
   */
  @Override
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    HBox mainHorizontalContainer = new HBox(10);
    mainHorizontalContainer.getChildren().addAll(
        getMatrixAndVectorInput(), getCoordinatesInput(),
        createAddTransformAndPreviousTransformButtons(), createSaveAndNextTransformButtons());

    mainLayout.getChildren().add(mainHorizontalContainer);
    return mainLayout;
  }

  public VBox createAddTransformAndPreviousTransformButtons() {
    VBox buttons = new VBox(createAddTransformButton(), createPreviousTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  public VBox createSaveAndNextTransformButtons() {
    VBox buttons = new VBox(createSaveButton(), createNextTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  public Button createPreviousTransformButton() {
    Button previousTransformButton = new Button("Previous transform");
    previousTransformButton.setOnAction(this::previousTransformButtonAction);
    return previousTransformButton;
  }

  public void previousTransformButtonAction(ActionEvent actionEvent) {
    AffineTransform2D transform2D;
    try {
      transform2D = getTransformFromInput();
      affineTransforms.set(currentIndex, transform2D);
      setAffineTransformScene(affineTransforms.get(currentIndex-1));
      currentIndex--;
    } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
    }
  }

  public AffineTransform2D getTransformFromInput() throws NumberFormatException{
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Invalid input");
      throw new NumberFormatException("Invalid input");
    } else {
      Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(a00.getText()),
          Double.parseDouble(a01.getText()), Double.parseDouble(a10.getText()),
          Double.parseDouble(a11.getText()));
      Vector2D vector = new Vector2D(Double.parseDouble(x0.getText()), Double.parseDouble(x1.getText()));
      return new AffineTransform2D(matrix, vector);
    }
  }

  public Button createNextTransformButton() {
    Button nextTransformButton = new Button("Next transform");
    nextTransformButton.setOnAction(this::nextTransformButtonAction);
    return nextTransformButton;
  }

  public void nextTransformButtonAction(ActionEvent actionEvent) {
    AffineTransform2D transform2D;
    try {
      transform2D = getTransformFromInput();
      affineTransforms.set(currentIndex, transform2D);
      setAffineTransformScene(affineTransforms.get(currentIndex+1));
      currentIndex++;
    } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
    }
  }

  @Override
  public void addAffineTransformAction(ActionEvent actionEvent) {
    try {
      affineTransforms.set(currentIndex, getTransformFromInput());
    } catch (NumberFormatException e) {
      return;
    }
    AffineTransform2D tempTransform = new AffineTransform2D(new Matrix2x2(0, 0, 0, 0), new Vector2D(0, 0));
    setAffineTransformScene(tempTransform);
    affineTransforms.add(tempTransform);
    currentIndex = affineTransforms.size() - 1;
  }

  @Override
  public void saveButtonAction(ActionEvent actionEvent) {
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    try {
      affineTransforms.set(currentIndex, getTransformFromInput());
    } catch (NumberFormatException e){
      return;
    }
    chaosGameController.setChaosGame(
        new ChaosGameDescription(getMinCoords(), getMaxCoords(), affineTransforms));
    stage.close();
  }

}

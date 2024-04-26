package edu.ntnu.stud.views.affinetransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.stud.views.affinetransformviews.AbstractAffineTransformView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View class for adding new AffineTransform based transformation
 * to the ChaosGame fractal.
 * <p>
 * This class provides a GUI for adding new AffineTransform2D transformations
 * to the ChaosGame fractal. The user can input the matrix and vector
 * of the transformation, as well as the min and max coordinates of the fractal.
 * </p>
 *
 * @author Scott du Plessis, Stanislovas Mockus
 * @version 1.0
 * @see ChaosGameController
 * @see AffineTransform2D
 * @see ChaosGameDescription
 */
public class AddAffineTransformView extends AbstractAffineTransformView {
  private final List<Transform2D> affineTransforms = new ArrayList<>();

  public AddAffineTransformView() {
    super();
  }

  /**
   * Method that sets the scene of the stage.
   */
  @Override
  public void setScene() {
    scene.setRoot(setMainLayout());
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
        getMatrixAndVectorInput(), getCoordinatesInput(), createAddTransformButton(), createSaveButton());

    mainLayout.getChildren().add(mainHorizontalContainer);
    return mainLayout;
  }

  /**
   * The action done when the add transform button is clicked.
   * The method checks if the input is invalid, and if it is, shows an error alert.
   * If the input is valid, the method calls {@link #addTransformToList()} to add the
   * transformation to the list of transformations. The method then clears the text-fields.
   *
   * @param actionEvent the event that is triggered when the button is clicked
   */
  @Override
  public void addAffineTransformAction(ActionEvent actionEvent) {
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    addTransformToList();
    clearTextFields();
  }

  /**
   * The action done when the save button is clicked.
   * The method checks if the input is invalid, and if it is, shows an error alert.
   * If the input is valid, the method calls {@link #addTransformToList()} to add the
   * transformation to the list of transformations. The method then sets the chaos game
   * with the new transformations and min and max coordinates, and closes the stage.
   *
   * @param actionEvent the event that is triggered when the button is clicked
   */
  @Override
  public void saveButtonAction(ActionEvent actionEvent) {
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    addTransformToList();
    chaosGameController.setChaosGame(
        new ChaosGameDescription(getMinCoords(), getMaxCoords(), getTransformList()));
    stage.close();
  }

  /**
   * Method that gets the values from the textboxes of the matrix and the vector.
   * And creates a new affine transformation using these values.
   * The Transformation is then added to the list of transformations {@code affineTransforms}
   */
  public void addTransformToList() {
    Matrix2x2 newMatrix = new Matrix2x2(Double.parseDouble(a00.getText()),
        Double.parseDouble(a01.getText()), Double.parseDouble(a10.getText()),
        Double.parseDouble(a11.getText()));

    Vector2D newVector = new Vector2D(
        Double.parseDouble(x0.getText()), Double.parseDouble(x1.getText()));

    affineTransforms.add(new AffineTransform2D(newMatrix, newVector));

    showAddedTransformAlert();
  }

  /**
   * Method that returns the list of transforms.
   *
   * @return affineTransforms, list with Transform2D objects.
   */
  public List<Transform2D> getTransformList() {
    return affineTransforms;
  }

  /**
   * Method that clears the text-fields after a transformation has been added.
   * The text-fields are set to empty strings, and the min and max coordinates
   * text-fields are disabled. So that the user can't add a new transformation
   * without entering new min and max coordinates.
   */
  public void clearTextFields() {
    a00.clear();
    a01.clear();
    a10.clear();
    a11.clear();
    x0.clear();
    x1.clear();
    minX0.setDisable(true);
    minX1.setDisable(true);
    maxX0.setDisable(true);
    maxX1.setDisable(true);
  }

  /**
   * Method that shows an alert to the user after a transformation has been added.
   * The alert is a confirmation message that the transformation has been added.
   */
  private void showAddedTransformAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Transformation added");

    alert.showAndWait();
  }
}

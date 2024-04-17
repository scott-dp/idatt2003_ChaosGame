package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
public class AddAffineTransformView {
  private final ChaosGameController chaosGameController = AppView.getChaosGameController();

  private final ArrayList<Transform2D> affineTransforms = new ArrayList<>();

  private final Stage stage = new Stage();
  private final Scene scene = new Scene(new VBox());

  //Elements in matrix
  TextField a0;
  TextField a1;
  TextField b0;
  TextField b1;

  //Elements in Vector
  TextField x0;
  TextField x1;

  //Elements minCoords
  private TextField minX0;
  private TextField minX1;

  //Elements maxCoords
  private TextField maxX0;
  private TextField maxX1;

  /**
   * Method that sets the scene of the stage.
   */
  public void setScene() {
    this.scene.setRoot(setMainLayout());
  }

  /**
   * Method that sets the main layout of the scene.
   * The layout consists of a horizontal container that holds two vertical containers,
   * used for entering the matrix and vector of the transformation, and the min and max coordinates
   * of the fractal.
   *
   * @return VBox mainLayout
   */
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    VBox matrixVectors = new VBox(10);
    matrixVectors.getChildren().addAll(enterMatrix(), enterVector());

    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(enterMinCoords(), enterMaxCoords());

    HBox mainHorizontalContainer = new HBox(10);
    mainHorizontalContainer.getChildren().addAll(
        matrixVectors, coordinatesInput, addTransformButton(), saveButton());

    mainLayout.getChildren().add(mainHorizontalContainer);
    return mainLayout;
  }

  /**
   * Method that creates a horizontal container for entering the matrix of the transformation.
   * The matrix layout consists of two horizontal containers, within a vertical container.
   * The horizontal containers hold text-fields which represent the matrix elements,
   * in their respective positions.
   *
   * @return HBox matrixHorizontal
   */
  public HBox enterMatrix() {
    HBox matrixHorizontal = new HBox(10);
    Label matrixLabel = new Label("Matrix: ");
    matrixHorizontal.getChildren().add(matrixLabel);

    HBox topNumbersContainer = new HBox(10);
    a0 = new TextField("0");
    a1 = new TextField("0");
    topNumbersContainer.getChildren().addAll(a0, a1);

    HBox bottomNumbersContainer = new HBox(10);
    b0 = new TextField("0");
    b1 = new TextField("0");
    bottomNumbersContainer.getChildren().addAll(b0, b1);

    VBox matrixLayout = new VBox();
    matrixLayout.getChildren().addAll(topNumbersContainer, bottomNumbersContainer);

    matrixHorizontal.getChildren().add(matrixLayout);
    return matrixHorizontal;
  }

  /**
   * Method that creates a horizontal container for entering the vector of the transformation.
   * The vector layout consists of a horizontal container, within a vertical container.
   * The horizontal containers hold text-fields which represent the vector elements,
   * in their respective positions. The vector layout is then added to the main layout.
   *
   * @return HBox vectorHorizontalContainer
   */
  public HBox enterVector() {
    HBox vectorHorizontalContainer = new HBox(10);
    Label vectorLabel = new Label("Vector: ");

    vectorHorizontalContainer.getChildren().add(vectorLabel);
    VBox vectorLayout = new VBox(10);
    x0 = new TextField("0");
    x1 = new TextField("0");
    vectorLayout.getChildren().addAll(x0, x1);

    vectorHorizontalContainer.getChildren().add(vectorLayout);

    return vectorHorizontalContainer;
  }

  /**
   * A method that creates a horizontal container for entering the min coordinates of the fractal.
   * The min coordinates layout consists of a horizontal container, within a vertical container.
   * The horizontal container holds text-fields which represent the min coordinates of the fractal.
   *
   * @return HBox minCoordsContainer
   */
  public HBox enterMinCoords() {
    HBox minCoordsContainer = new HBox(10);
    Label minCoordsLabel = new Label("Min Coords: ");
    minCoordsContainer.getChildren().add(minCoordsLabel);
    VBox minCoordsLayout = new VBox(10);

    minX0 = new TextField("0");
    minX1 = new TextField("0");

    minCoordsLayout.getChildren().addAll(minX0, minX1);

    minCoordsContainer.getChildren().add(minCoordsLayout);
    return minCoordsContainer;
  }

  /**
   * A method that creates a horizontal container for entering the max coordinates of the fractal.
   * The max coordinates layout consists of a horizontal container, within a vertical container.
   * The horizontal container holds text-fields which represent the max coordinates of the fractal.
   *
   * @return HBox maxCoordsContainer
   */
  public HBox enterMaxCoords() {
    HBox maxCoordsContainer = new HBox(10);
    Label maxCoordsLabel = new Label("Max Coords: ");
    maxCoordsContainer.getChildren().add(maxCoordsLabel);
    VBox minCoordsLayout = new VBox(10);

    maxX0 = new TextField("0");
    maxX1 = new TextField("0");

    minCoordsLayout.getChildren().addAll(maxX0, maxX1);

    maxCoordsContainer.getChildren().add(minCoordsLayout);
    return maxCoordsContainer;
  }

  /**
   * Method that creates a button that adds the affine transformation to the list of
   * transformations. When clicked the button calls {@link #createTransformation()}
   * and {@link #clearTextFields()}. In order to add the transformation to the list and
   * clear the text-fields, so that new numbers can be entered. The button also calls
   * {@link #showAddedAlert()} to show a confirmation message to the user.
   *
   * @return Button addTransformButton
   */
  public Button addTransformButton() {
    Button addTransformButton = new Button("Add Affine Transform");
    addTransformButton.setOnAction(this::addAffineTransformAction);
    return addTransformButton;
  }

  public void addAffineTransformAction(ActionEvent actionEvent) {
    if (!isInputValid()) {
      showInvalidInputAlert("Input is invalid");
      return;
    }
    createTransformation();
    clearTextFields();
  }

  /**
   * Method that creates a button that saves the transformations and closes the stage.
   * When clicked the button calls {@link ChaosGameController#setChaosGame(ChaosGameDescription)}
   * to set the chaos game with the new transformations that the user has added.
   * And the min and max coordinates that the user has entered.
   * The button then closes the stage.
   *
   * @return Button saveButton
   */
  public Button saveButton() {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(this::saveAffineTransformsAction);
    return saveButton;
  }

  public void saveAffineTransformsAction(ActionEvent actionEvent) {
    if (!isInputValid()) {
      showInvalidInputAlert("Input is invalid");
      return;
    }
    createTransformation();
    chaosGameController.setChaosGame(
        new ChaosGameDescription(getMinCoords(), getMaxCoords(), getTransformList()));
    stage.close();
  }

  /**
   * Method that shows the stage.
   * The method calls {@link #setScene()} to set the scene of the stage.
   * The method then sets the scene of the stage and shows the stage.
   */
  public void showStage() {
    setScene();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }

  /**
   * Method that gets the values from the textboxes of the matrix and the vector.
   * And creates a new affine transformation using these values.
   * The Transformation is then added to the list of transformations {@code affineTransforms}
   */
  public void createTransformation() {
    Matrix2x2 newMatrix = new Matrix2x2(Double.parseDouble(a0.getText()),
        Double.parseDouble(a1.getText()), Double.parseDouble(b0.getText()),
        Double.parseDouble(b1.getText()));

    Vector2D newVector = new Vector2D(
        Double.parseDouble(x0.getText()), Double.parseDouble(x1.getText()));

    affineTransforms.add(new AffineTransform2D(newMatrix, newVector));

    showAddedAlert();
  }

  public boolean isInputValid() {
    try {
      Double.parseDouble(a0.getText());
      Double.parseDouble(a1.getText());
      Double.parseDouble(b0.getText());
      Double.parseDouble(b1.getText());
      Double.parseDouble(x0.getText());
      Double.parseDouble(x1.getText());
      double minX0parsed = Double.parseDouble(minX0.getText());
      double minX1parsed = Double.parseDouble(minX1.getText());
      double maxX0parsed = Double.parseDouble(maxX0.getText());
      double maxX1parsed = Double.parseDouble(maxX1.getText());
      ChaosGameUtils.validateMinAndMaxCoords(
          new Vector2D(minX0parsed, minX1parsed), new Vector2D(maxX0parsed, maxX1parsed));
      return true;
    } catch (NumberFormatException e) {
      return false;
    } catch (IllegalArgumentException e) {
      showInvalidInputAlert(e.getMessage());
      return false;
    }
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
   * method that parses the values in the minimum coordinates text fields,
   * and creates a new Vector2D object.
   *
   * @return Vector2D object representing the minimum coordinates
   */
  public Vector2D getMinCoords() {
    return new Vector2D(Double.parseDouble(minX0.getText()), Double.parseDouble(minX1.getText()));
  }

  /**
   * method that parses the values in the maximum coordinates text fields,
   * and creates a new Vector2D object.
   *
   * @return Vector2D object representing the maximum coordinates
   */
  public Vector2D getMaxCoords() {
    return new Vector2D(Double.parseDouble(maxX0.getText()), Double.parseDouble(maxX1.getText()));
  }

  /**
   * Method that clears the text-fields after a transformation has been added.
   * The text-fields are set to empty strings, and the min and max coordinates
   * text-fields are disabled. So that the user can't add a new transformation
   * without entering new min and max coordinates.
   */
  public void clearTextFields() {
    a0.clear();
    a1.clear();
    b0.clear();
    b1.clear();
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
  private void showAddedAlert() {
    // Create an alert
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null); // No header
    alert.setContentText("Transformation added");

    // Display the alert and wait for it to be dismissed
    alert.showAndWait();
  }

  /**
   * Method that shows an alert to the user if the input is invalid.
   *
   * @param errorMessage the error message to be displayed
   */
  public void showInvalidInputAlert(String errorMessage) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Error");
    alert.setHeaderText(null); // No header
    alert.setContentText(errorMessage);

    alert.showAndWait();
  }

}

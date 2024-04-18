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
  TextField a00;
  TextField a01;
  TextField a10;
  TextField a11;

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

    VBox matrixAndVectorInput = new VBox(10);
    matrixAndVectorInput.getChildren().addAll(createMatrixInput(), createVectorInput());

    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(createMinCoordsInput(), createMaxCoordsInput());

    HBox mainHorizontalContainer = new HBox(10);
    mainHorizontalContainer.getChildren().addAll(
        matrixAndVectorInput, coordinatesInput, addTransformButton(), createSaveButton());

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
  public HBox createMatrixInput() {
    HBox matrixHorizontal = new HBox(10);
    Label matrixLabel = new Label("Matrix: ");
    matrixHorizontal.getChildren().add(matrixLabel);

    HBox topNumbersContainer = new HBox(10);
    a00 = new TextField("0");
    a01 = new TextField("0");
    topNumbersContainer.getChildren().addAll(a00, a01);

    HBox bottomNumbersContainer = new HBox(10);
    a10 = new TextField("0");
    a11 = new TextField("0");
    bottomNumbersContainer.getChildren().addAll(a10, a11);

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
  public HBox createVectorInput() {
    HBox vectorHorizontalContainer = new HBox(10);
    Label vectorLabel = new Label("Vector: ");

    vectorHorizontalContainer.getChildren().add(vectorLabel);
    VBox vectorInputLayout = new VBox(10);
    x0 = new TextField("0");
    x1 = new TextField("0");
    vectorInputLayout.getChildren().addAll(x0, x1);

    vectorHorizontalContainer.getChildren().add(vectorInputLayout);

    return vectorHorizontalContainer;
  }

  /**
   * A method that creates a horizontal container for entering the min coordinates of the fractal.
   * The min coordinates layout consists of a horizontal container, within a vertical container.
   * The horizontal container holds text-fields which represent the min coordinates of the fractal.
   *
   * @return HBox minCoordsContainer
   */
  public HBox createMinCoordsInput() {
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
  public HBox createMaxCoordsInput() {
    HBox maxCoordsContainer = new HBox(10);
    Label maxCoordsLabel = new Label("Max Coords: ");
    maxCoordsContainer.getChildren().add(maxCoordsLabel);
    VBox maxCoordsLayout = new VBox(10);

    maxX0 = new TextField("0");
    maxX1 = new TextField("0");

    maxCoordsLayout.getChildren().addAll(maxX0, maxX1);

    maxCoordsContainer.getChildren().add(maxCoordsLayout);
    return maxCoordsContainer;
  }

  /**
   * Method that creates a button that adds the affine transformation to the list of
   * transformations. When clicked the button calls {@link #addTransformToList()}
   * and {@link #clearTextFields()}. In order to add the transformation to the list and
   * clear the text-fields, so that new numbers can be entered. The button also calls
   * {@link #showAddedTransformAlert()} to show a confirmation message to the user.
   *
   * @return Button addTransformButton
   */
  public Button addTransformButton() {
    Button addTransformButton = new Button("Add Affine Transform");
    addTransformButton.setOnAction(this::addAffineTransformAction);
    return addTransformButton;
  }

  /**
   * The action done when the add transform button is clicked.
   * The method checks if the input is invalid, and if it is, shows an error alert.
   * If the input is valid, the method calls {@link #addTransformToList()} to add the
   * transformation to the list of transformations. The method then clears the text-fields.
   *
   * @param actionEvent the event that is triggered when the button is clicked
   */
  public void addAffineTransformAction(ActionEvent actionEvent) {
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    addTransformToList();
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
  public Button createSaveButton() {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(this::saveButtonAction);
    return saveButton;
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
   * Method that checks if the input given in the textfields is INVALID.
   *
   * @return true if the input is invalid, false if the input is valid.
   */
  public boolean isInputInvalid() {
    try {
      Double.parseDouble(a00.getText());
      Double.parseDouble(a01.getText());
      Double.parseDouble(a10.getText());
      Double.parseDouble(a11.getText());
      Double.parseDouble(x0.getText());
      Double.parseDouble(x1.getText());
      double minX0parsed = Double.parseDouble(minX0.getText());
      double minX1parsed = Double.parseDouble(minX1.getText());
      double maxX0parsed = Double.parseDouble(maxX0.getText());
      double maxX1parsed = Double.parseDouble(maxX1.getText());
      ChaosGameUtils.validateMinAndMaxCoords(
          new Vector2D(minX0parsed, minX1parsed), new Vector2D(maxX0parsed, maxX1parsed));
      return false;
    } catch (NumberFormatException e) {
      return true;
    } catch (IllegalArgumentException e) {
      ChaosGameUtils.showErrorAlert(e.getMessage());
      return true;
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
    // Create an alert
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Transformation added");

    // Display the alert and wait for it to be dismissed
    alert.showAndWait();
  }
}

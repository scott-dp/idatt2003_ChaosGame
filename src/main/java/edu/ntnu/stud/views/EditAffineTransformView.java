package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;

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
import javafx.scene.transform.Affine;
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
public class EditAffineTransformView {
  int currentIndex;
  private final ChaosGameController chaosGameController;

  private final List<Transform2D> affineTransforms;
  private final Stage stage;
  private final Scene scene;

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
  public EditAffineTransformView() {
    currentIndex = 0;
    chaosGameController = AppView.getChaosGameController();
    affineTransforms = chaosGameController.getChaosGame().getDescription().getTransforms();
    stage = new Stage();
    scene = new Scene(new VBox());
  }
  /**
   * Method that sets the scene of the stage.
   */
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
    AffineTransform2D affineTransform = (AffineTransform2D) transform;

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
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    VBox matrixAndVectorInput = new VBox(10);
    matrixAndVectorInput.getChildren().addAll(createMatrixInput(), createVectorInput());

    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(createMinCoordsInput(), createMaxCoordsInput());

    HBox mainHorizontalContainer = new HBox(10);

    mainHorizontalContainer.getChildren().addAll(
        matrixAndVectorInput, coordinatesInput, createAddTransformAndPreviousTransformButtons(), createSaveAndNextTransformButtons());

    mainLayout.getChildren().add(mainHorizontalContainer);
    return mainLayout;
  }

  public VBox createAddTransformAndPreviousTransformButtons() {
    VBox buttons = new VBox(addTransformButton(), previousTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  public VBox createSaveAndNextTransformButtons() {
    VBox buttons = new VBox(createSaveButton(), nextTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  public Button previousTransformButton() {
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

  public AffineTransform2D getTransformFromInput() {
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

  public Button nextTransformButton() {
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
    a00 = new TextField();
    a01 = new TextField();
    topNumbersContainer.getChildren().addAll(a00, a01);

    HBox bottomNumbersContainer = new HBox(10);
    a10 = new TextField();
    a11 = new TextField();
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
    x0 = new TextField();
    x1 = new TextField();
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

    minX0 = new TextField();
    minX1 = new TextField();

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

    maxX0 = new TextField();
    maxX1 = new TextField();

    maxCoordsLayout.getChildren().addAll(maxX0, maxX1);

    maxCoordsContainer.getChildren().add(maxCoordsLayout);
    return maxCoordsContainer;
  }

  public Button addTransformButton() {
    Button addTransformButton = new Button("Add Affine Transform");
    addTransformButton.setOnAction(this::addAffineTransformAction);
    return addTransformButton;
  }

  public void addAffineTransformAction(ActionEvent actionEvent) {
    AffineTransform2D tempTransform = new AffineTransform2D(new Matrix2x2(0, 0, 0, 0), new Vector2D(0, 0));
    setAffineTransformScene(tempTransform);
    affineTransforms.add(tempTransform);
    currentIndex = affineTransforms.size() - 1;
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

  public void saveButtonAction(ActionEvent actionEvent) {
    if (isInputInvalid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    affineTransforms.set(currentIndex, getTransformFromInput());
    chaosGameController.setChaosGame(
        new ChaosGameDescription(getMinCoords(), getMaxCoords(), affineTransforms));
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
}

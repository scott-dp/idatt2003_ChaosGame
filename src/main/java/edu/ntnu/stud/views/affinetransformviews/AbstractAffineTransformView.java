package edu.ntnu.stud.views.affinetransformviews;

import edu.ntnu.stud.models.Coordinate;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAffineTransformView {
  protected List<Transform2D> affineTransforms;
  protected int currentIndex;
  protected final Stage stage;
  protected final Scene scene;

  //Elements in matrix
  protected TextField a00;
  protected TextField a01;
  protected TextField a10;
  protected TextField a11;

  //Elements in Vector
  protected TextField x0;
  protected TextField x1;

  //Elements minCoords
  protected TextField minX0;
  protected TextField minX1;

  //Elements maxCoords
  protected TextField maxX0;
  protected TextField maxX1;

  protected AbstractAffineTransformView() {
    currentIndex = 0;
    affineTransforms = new ArrayList<>();
    this.stage = new Stage();
    this.scene = new Scene(new VBox());
    a00 = new TextField();
    a01 = new TextField();
    a10 = new TextField();
    a11 = new TextField();
    x0 = new TextField();
    x1 = new TextField();
    minX0 = new TextField();
    minX1 = new TextField();
    maxX0 = new TextField();
    maxX1 = new TextField();
  }

  public abstract void setScene();

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
   * A method that creates a horizontal container for entering the max coordinates of the fractal.
   * The max coordinates layout consists of a horizontal container, within a vertical container.
   * The horizontal container holds text-fields which represent the max coordinates of the fractal.
   *
   * @return HBox maxCoordsContainer
   */
  public HBox createCoordinateInput(TextField x0, TextField x1, Coordinate coordinate) {
    HBox coordsContainer = new HBox(10);
    Label coordsLabel = new Label();

    switch (coordinate) {
      case MAX ->     coordsLabel.setText("Max coords: ");
      case MIN ->     coordsLabel.setText("Min coords: ");
    }

    coordsContainer.getChildren().add(coordsLabel);
    VBox coordsLayout = new VBox(10);

    coordsLayout.getChildren().addAll(x0, x1);

    coordsContainer.getChildren().add(coordsLayout);
    return coordsContainer;
  }

  /**
   * Method that creates a button which has the action {@link #saveButtonAction(ActionEvent)}
   * linked to it.
   *
   * @return Button saveButton
   */
  public Button createSaveButton() {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(this::saveButtonAction);
    return saveButton;
  }

  /**
   * The method called when the "Save" button is clicked.
   *
   * @param actionEvent the event that occurred.
   */
  public abstract void saveButtonAction(ActionEvent actionEvent);

  /**
   * Method that creates a button which has the action {@link #addAffineTransformAction(ActionEvent)}
   * linked to it.
   *
   * @return Button addTransformButton
   */
  public Button createAddTransformButton() {
    Button addTransformButton = new Button("Add Affine Transform");
    addTransformButton.setOnAction(this::addAffineTransformAction);
    return addTransformButton;
  }

  /**
   * The method called when the "Add transform" button is clicked in the GUI.
   *
   * @param actionEvent the event that occurred.
   */
  public abstract void addAffineTransformAction(ActionEvent actionEvent);

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
    vectorInputLayout.getChildren().addAll(x0, x1);

    vectorHorizontalContainer.getChildren().add(vectorInputLayout);

    return vectorHorizontalContainer;
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
    topNumbersContainer.getChildren().addAll(a00, a01);

    HBox bottomNumbersContainer = new HBox(10);
    bottomNumbersContainer.getChildren().addAll(a10, a11);

    VBox matrixLayout = new VBox();
    matrixLayout.getChildren().addAll(topNumbersContainer, bottomNumbersContainer);

    matrixHorizontal.getChildren().add(matrixLayout);
    return matrixHorizontal;
  }

  public abstract VBox setMainLayout();

  public VBox getMatrixAndVectorInput() {
    VBox matrixAndVectorInput = new VBox(10);
    matrixAndVectorInput.getChildren().addAll(createMatrixInput(), createVectorInput());
    return matrixAndVectorInput;
  }

  public VBox getCoordinatesInput() {
    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(createCoordinateInput(minX0, minX1, Coordinate.MIN),
        createCoordinateInput(maxX0, maxX1, Coordinate.MAX));
    return coordinatesInput;
  }

  public void setTransformNumber() {
    int transformNumber = currentIndex + 1;
    stage.setTitle("Transform number " + transformNumber);
  }
}

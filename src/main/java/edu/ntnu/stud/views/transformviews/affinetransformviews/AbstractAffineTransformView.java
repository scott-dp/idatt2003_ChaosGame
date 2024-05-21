package edu.ntnu.stud.views.transformviews.affinetransformviews;

import edu.ntnu.stud.models.Coordinate;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.transformviews.TransformView;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * An abstract class that represents a view in the app that has to do with affine transforms.
 * Contains methods with implementations and abstract methods that have to be implemented
 * in the subclasses that inherit from this.
 *
 * @author Scott du Plessis, Stasys Mockus
 * @version x.x
 */
public abstract class AbstractAffineTransformView extends TransformView {
  protected List<Transform2D> affineTransforms;
  protected int currentIndex;
  //Elements in matrix
  protected TextField a00;
  protected TextField a01;
  protected TextField a10;
  protected TextField a11;

  //Elements in Vector
  protected TextField x0;
  protected TextField x1;

  /**
   * Constructor to initialize fields.
   */
  protected AbstractAffineTransformView() {
    super();
    currentIndex = 0;
    affineTransforms = new ArrayList<>();
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
  public boolean isInputValid() {
    try {
      Double.parseDouble(a00.getText());
      Double.parseDouble(a01.getText());
      Double.parseDouble(a10.getText());
      Double.parseDouble(a11.getText());
      Double.parseDouble(x0.getText());
      Double.parseDouble(x1.getText());
      validateCoords();
      return true;
    } catch (NumberFormatException e) {
      return false;
    } catch (IllegalArgumentException e) {
      ChaosGameUtils.showErrorAlert(e.getMessage());
      return false;
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
   * Method that creates a button which has the action {@link #saveButtonAction(ActionEvent)}
   * linked to it.
   *
   * @return Button saveButton
   */
  public Button createSaveButton() {
    Button saveButton = new Button("Save & Finish");
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
   * Method that creates a button which has the action
   * {@link #addAffineTransformAction(ActionEvent)} linked to it.
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

  /**
   * Creates a container for the matrix and vector input and calls submethods to create
   * the specific matrix and vector inputs.
   *
   * @return a VBox containing the matrix and vector input fields.
   */
  public VBox getMatrixAndVectorInput() {
    VBox matrixAndVectorInput = new VBox(10);
    matrixAndVectorInput.getChildren().addAll(createMatrixInput(), createVectorInput());
    return matrixAndVectorInput;
  }

  /**
   * Creates a container that has the input for min and max coords inside of it.
   *
   * @return return a VBox with the coordinate input fields
   */
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

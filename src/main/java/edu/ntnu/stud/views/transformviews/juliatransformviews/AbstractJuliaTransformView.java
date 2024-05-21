package edu.ntnu.stud.views.transformviews.juliatransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.Coordinate;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import java.util.ArrayList;

import edu.ntnu.stud.views.transformviews.TransformView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * An abstract class that represents a view in the app that has to do with Julia transforms.
 * Contains methods with implementations and abstract methods that have to be implemented
 * in the subclasses that inherit from this.
 *
 * @author Scott du Plessis, Stasys Mockus
 * @version x.x
 */
public abstract class AbstractJuliaTransformView extends TransformView {
  protected TextField imaginaryPartField;
  protected TextField realPartField;

  /**
   * Constructor to initialize fields.
   */
  protected AbstractJuliaTransformView() {
    super();
    imaginaryPartField = new TextField();
    realPartField = new TextField();
    minX0 = new TextField();
    minX1 = new TextField();
    maxX0 = new TextField();
    maxX1 = new TextField();
  }

  public abstract void setScene();

  /**
   * Method to check if the input is valid.
   *
   * @return ture if input is valid, false if not.
   */
  public boolean isInputValid() {
    try {
      Double.parseDouble(realPartField.getText());
      Double.parseDouble(imaginaryPartField.getText());
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
   * Method to show the stage.
   */
  public void showStage() {
    setScene();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }

  /**
   * Method that create the save button and adds an action to it.
   * The action is {@link #saveButtonAction(ActionEvent)}.
   *
   * @return the save button
   */
  public Button createSaveButton() {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(this::saveButtonAction);
    return saveButton;
  }

  /**
   * Method that creates the input for the complex number.
   *
   * @return VBox with the input fields for the complex number
   */
  public VBox createNumeralInput() {
    HBox realPartContainer = new HBox(10);
    Label realPartLabel = new Label("Real part:");
    realPartField = new TextField("Enter Real Part");
    realPartContainer.getChildren().addAll(realPartLabel, realPartField);

    HBox imaginaryPartContainer = new HBox(10);
    Label imaginaryPartLabel = new Label("Imaginary part:");
    imaginaryPartField = new TextField("Enter Imaginary Part");
    imaginaryPartContainer.getChildren().addAll(imaginaryPartLabel, imaginaryPartField);

    VBox numeralContainer = new VBox(10);
    numeralContainer.getChildren().addAll(realPartContainer, imaginaryPartContainer);
    return numeralContainer;
  }

  public abstract VBox setMainLayout();


  /**
   * Method that creates the input for the coordinates.
   *
   * @return VBox with the input fields for the coordinates
   */
  public VBox getCoordinatesInput() {
    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(createCoordinateInput(minX0, minX1, Coordinate.MIN),
        createCoordinateInput(maxX0, maxX1, Coordinate.MAX));
    return coordinatesInput;
  }

  /**
   * The action done when the save button is clicked. Saves the input and changes the chaos game.
   */
  public void saveButtonAction(ActionEvent event) {
    if (!isInputValid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
    } else {
      ChaosGameController.getInstance().setChaosGameDescription(createJuliaDescription());
      stage.close();
    }
  }

  /**
   * Method that creates a ChaosGameDescription object with the Julia transform
   * input from the user.
   *
   * @return ChaosGameDescription object with input from the user.
   */
  public ChaosGameDescription createJuliaDescription() {
    ArrayList<Transform2D> transforms = new ArrayList<>();
    transforms.add(new JuliaTransform(createComplex(), 1));
    transforms.add(new JuliaTransform(createComplex(), -1));
    return new ChaosGameDescription(createMinCoordinates(), createMaxCoordinates(), transforms);
  }

  /**
   * Method that creates a {@link Complex} instance with input from the user.
   *
   * @return Complex instance with input from the user.
   */
  public Complex createComplex() {
    return new Complex(Double.parseDouble(realPartField.getText()),
        Double.parseDouble(imaginaryPartField.getText()));
  }

  /**
   * Method that creates a {@link Vector2D} instance with input from the user. Specifically
   * the min coordinates.
   *
   * @return Vector2D instance with input from the user.
   */
  public Vector2D createMinCoordinates() {
    return new Vector2D(Double.parseDouble(minX0.getText()), Double.parseDouble(minX1.getText()));
  }

  /**
   * Method that creates a {@link Vector2D} instance with input from the user. Specifically
   * the max coordinates.
   *
   * @return Vector2D instance with input from the user.
   */
  public Vector2D createMaxCoordinates() {
    return new Vector2D(Double.parseDouble(maxX0.getText()), Double.parseDouble(maxX1.getText()));
  }
}

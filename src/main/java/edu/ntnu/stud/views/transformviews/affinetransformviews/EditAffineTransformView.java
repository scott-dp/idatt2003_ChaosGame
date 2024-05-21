package edu.ntnu.stud.views.transformviews.affinetransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.mathematics.Matrix2x2;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.transform.Transform2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * A view in the application that shows the edit window if the current fractal is
 * affine transform based.
 *
 * @author Scott du Plessis, Stasys Mockus
 * @version x.x
 */
public class EditAffineTransformView extends AbstractAffineTransformView {
  private static final Logger LOGGER = Logger.getLogger(EditAffineTransformView.class.getName());

  /**
   * Constructor for this class. Calls the superclass' constructor and does some necessary
   * initialization.
   */
  public EditAffineTransformView() {
    super();
    affineTransforms = ChaosGameController.getInstance()
            .getChaosGame().getDescription().getTransforms();
  }

  /**
   * Method that sets the scene of the stage.
   */
  @Override
  public void setScene() {
    scene.setRoot(setMainLayout());
    setAffineTransformScene(affineTransforms.get(0));
    setCoordsInView(ChaosGameController.getInstance().getChaosGame().getDescription());
    setTransformNumber();
  }

  /**
   * Sets the coordinates of a fractal in the edit window.
   *
   * @param description the description which describes the current fractal.
   */
  public void setCoordsInView(ChaosGameDescription description) {
    minX0.setText(String.valueOf(description.getMinCoords().getX0()));
    minX1.setText(String.valueOf(description.getMinCoords().getX1()));
    maxX0.setText(String.valueOf(description.getMaxCoords().getX0()));
    maxX1.setText(String.valueOf(description.getMaxCoords().getX1()));
  }

  /**
   * Sets the matrix and vector of one of the transforms in the window.
   *
   * @param transform the transform to be shown in window
   */
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

  /**
   * Creates a container for the "Add transform" and "Previous transform" buttons
   * and calls methods to create those buttons.
   *
   * @return a VBox containing the buttons mentioned
   */
  public VBox createAddTransformAndPreviousTransformButtons() {
    VBox buttons = new VBox(createAddTransformButton(), createPreviousTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  /**
   * Creates a container which contains the "Save" and "Next transform" buttons and calls
   * methods to create those buttons.
   *
   * @return a VBox containing the mentioned buttons
   */
  public VBox createSaveAndNextTransformButtons() {
    VBox buttons = new VBox(createSaveButton(), createNextTransformButton());
    buttons.setSpacing(10);
    return buttons;
  }

  /**
   * Creates the "Previous transform" button and adds an action to it.
   *
   * @return the button which says "Previous transform"
   */
  public Button createPreviousTransformButton() {
    Button previousTransformButton = new Button("Previous transform");
    previousTransformButton.setOnAction(this::previousTransformButtonAction);
    return previousTransformButton;
  }

  /**
   * The action which shall fire when "Previous transform" button is pressed. The action
   * saves the current transform and shows the previous transform.
   *
   * @param actionEvent the event that happened
   */
  public void previousTransformButtonAction(ActionEvent actionEvent) {
    AffineTransform2D transform2D;
    try {
      transform2D = getTransformFromInput();
      affineTransforms.set(currentIndex, transform2D);
      setAffineTransformScene(affineTransforms.get(currentIndex - 1));
      currentIndex--;
      setTransformNumber();
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      //If there are no previous transforms to be shown
      LOGGER.log(Level.INFO, "No previous transform to show", e);
    }
  }

  /**
   * Tries to collect the set values and create an {@link AffineTransform2D} object out of the
   * input.
   *
   * @return an AffineTransform2D object with the given input
   * @throws NumberFormatException if the input is invalid
   */
  public AffineTransform2D getTransformFromInput() throws NumberFormatException {
    if (!isInputValid()) {
      ChaosGameUtils.showErrorAlert("Invalid input");
      throw new NumberFormatException("Invalid input");
    } else {
      Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(a00.getText()),
          Double.parseDouble(a01.getText()), Double.parseDouble(a10.getText()),
          Double.parseDouble(a11.getText()));
      Vector2D vector = new Vector2D(Double.parseDouble(x0.getText()),
          Double.parseDouble(x1.getText()));
      return new AffineTransform2D(matrix, vector);
    }
  }

  /**
   * Creates the "Next transform" button and adds an action to it.
   *
   * @return the button which says "Next transform"
   */
  public Button createNextTransformButton() {
    Button nextTransformButton = new Button("Next transform");
    nextTransformButton.setOnAction(this::nextTransformButtonAction);
    return nextTransformButton;
  }

  /**
   * The action which shall fire when "Next transform" button is pressed. The action
   * saves the current transform and shows the next transform.
   *
   * @param actionEvent the event that happened
   */
  public void nextTransformButtonAction(ActionEvent actionEvent) {
    AffineTransform2D transform2D;
    try {
      transform2D = getTransformFromInput();
      affineTransforms.set(currentIndex, transform2D);
      setAffineTransformScene(affineTransforms.get(currentIndex + 1));
      currentIndex++;
      setTransformNumber();
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      LOGGER.log(Level.INFO, "No next transform to show", e);
    }
  }

  /**
   * Method that is called when the "Add transform" button is clicked. Sets a transform in the
   * window with all values 0, and adds it to the list of transforms. The "blank" transform is
   * overridden if there are any changes done to it.
   */
  @Override
  public void addAffineTransformAction(ActionEvent actionEvent) {
    try {
      affineTransforms.set(currentIndex, getTransformFromInput());
    } catch (NumberFormatException e) {
      return;
    }
    AffineTransform2D tempTransform = new AffineTransform2D(
        new Matrix2x2(0, 0, 0, 0), new Vector2D(0, 0));
    setAffineTransformScene(tempTransform);
    affineTransforms.add(tempTransform);
    currentIndex = affineTransforms.size() - 1;
    setTransformNumber();
  }

  /**
   * Method that is called when the "Save" button is clicked. Checks if the input is valid,
   * and if it is, saves the current transform, changes the chaos game description and closes the
   * stage.
   */
  @Override
  public void saveButtonAction(ActionEvent actionEvent) {
    if (!isInputValid()) {
      ChaosGameUtils.showErrorAlert("Input is invalid");
      return;
    }
    try {
      affineTransforms.set(currentIndex, getTransformFromInput());
    } catch (NumberFormatException e) {
      return;
    }
    ChaosGameController.getInstance().setChaosGameDescription(
        new ChaosGameDescription(getMinCoords(), getMaxCoords(), affineTransforms));
    stage.close();
  }

}

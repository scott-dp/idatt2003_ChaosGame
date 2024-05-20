package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.transform.JuliaTransform;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * A class that represents a view for editing a Julia transform in the chaos game.
 * <p>
 *   This class provides a GUI so that the user can edit the desired values for the Julia transform.
 * </p>
 *
 * @author Stanislovas Mockus, Scott du Plessis
 * @version x.x
 */
public class EditJuliaTransformView extends AbstractJuliaTransformView {
  JuliaTransform currentTransform;

  /**
   * Constructor for this class. Calls the superclass' constructor and does some necessary
   * initialization.
   */
  public EditJuliaTransformView() {
    super();
    currentTransform = (JuliaTransform) ChaosGameController.getInstance().getChaosGame()
        .getDescription().getTransforms().get(0);
  }

  /**
   * Method that sets the scene of the stage.
   */
  @Override
  public void setScene() {
    this.scene.setRoot(setMainLayout());
    setCoordsInView();
    setTransformInView(currentTransform);
  }

  /**
   * Method that sets the main layout of the edit view.
   *
   * @return the main layout container of the edit view
   */
  @Override
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    mainLayout.getChildren().addAll(
        createNumeralInput(), getCoordinatesInput(), createSaveButton());

    return mainLayout;
  }

  /**
   * Sets the min and max coordinates of a fractal in the edit window.
   */
  public void setCoordsInView() {
    minX0.setText(String.valueOf(ChaosGameController.getInstance().getChaosGame()
        .getDescription().getMinCoords().getX0()));
    minX1.setText(String.valueOf(ChaosGameController.getInstance().getChaosGame()
        .getDescription().getMinCoords().getX1()));
    maxX0.setText(String.valueOf(ChaosGameController.getInstance().getChaosGame()
        .getDescription().getMaxCoords().getX0()));
    maxX1.setText(String.valueOf(ChaosGameController.getInstance().getChaosGame()
        .getDescription().getMaxCoords().getX1()));
  }

  /**
   * Sets the real and imaginary parts of the complex number in the edit window.
   *
   * @param transform the transform to be shown in window
   */
  public void setTransformInView(JuliaTransform transform) {
    realPartField.setText(String.valueOf(transform.getConstantPoint().getRealPart()));
    imaginaryPartField.setText(String.valueOf(transform.getConstantPoint().getImaginaryPart()));
  }
}

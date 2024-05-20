package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.transform.JuliaTransform;
import javafx.scene.layout.VBox;

/**
 * A class that represents a view for adding a new Julia transform to the chaos game.
 * <p>
 * This class provides a GUI so that the user can enter the desired values for the Julia transform.
 * The user can enter the real and imaginary parts of the complex number, as well as the min and
 * max coordinates. The user can then save the Julia transform so that it can later be
 * graphically displayed.
 * </p>
 *
 * @author Stanislovas Mockus, Scott du Plessis
 * @version 1.0
 * @see JuliaTransform
 * @see ChaosGameDescription
 */
public class AddJuliaTransformView extends AbstractJuliaTransformView {

  public AddJuliaTransformView() {
    super();
  }

  @Override
  public void setScene() {
    scene.setRoot(setMainLayout());
  }


  /**
   * Method that sets the main layout of the Interface.
   * The layout consists of a vertical container that contains the top row,
   * bottom row and a save button.
   *
   * @return mainLayout the main layout container of the interface
   */
  @Override
  public VBox setMainLayout() {
    VBox mainLayout = new VBox(10);

    mainLayout.getChildren().addAll(
        createNumeralInput(), getCoordinatesInput(), createSaveButton());

    return mainLayout;
  }
}
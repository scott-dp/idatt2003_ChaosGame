package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.Complex;
import edu.ntnu.stud.models.JuliaTransform;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.juliatransformviews.AbstractJuliaTransformView;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * A class that represents a view for adding a new Julia transform to the chaos game.
 * <p>
 * This class provides a GUI so that the user can enter the desired values for the Julia transform.
 * The user can enter the real and imaginary parts of the complex number, as well as the min and max coordinates.
 * The user can then save the Julia transform so that it can later be graphically displayed.
 * </p>
 *
 * @version 1.0
 * @author Stanislovas Mockus, Scott du Plessis
 * @see JuliaTransform
 * @see ChaosGameDescription
 * @see ChaosGameController
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
     * The layout consists of a vertical container that contains the top row, bottom row and a save button.
     *
     * @return mainLayout the main layout container of the interface
     */
    @Override
    public VBox setMainLayout() {
        VBox mainLayout = new VBox(10);

        mainLayout.getChildren().addAll(createNumeralInput(),getCoordinatesInput(),createSaveButton());

        return mainLayout;
    }

    @Override
    public void saveButtonAction(ActionEvent event) {
        if (!isInputValid()) {
            ChaosGameUtils.showErrorAlert("Input is invalid");
            return;
        } else {
            chaosGameController.setChaosGame(createJuliaDescription());
            stage.close();
        }
    }

    public ChaosGameDescription createJuliaDescription() {
        ArrayList<Transform2D> transforms = new ArrayList<>();
        transforms.add(new JuliaTransform(createComplex(), 1));
        transforms.add(new JuliaTransform(createComplex(), -1));
    return new ChaosGameDescription(createMinCoordinates(), createMaxCoordinates(), transforms);
    }

    public Complex createComplex() {
        return new Complex(Double.parseDouble(realPartField.getText()), Double.parseDouble(imaginaryPartField.getText()));
    }

    public Vector2D createMinCoordinates() {
       return new Vector2D(Double.parseDouble(minX0.getText()), Double.parseDouble(minX1.getText()));
    }

    public Vector2D createMaxCoordinates() {
        return new Vector2D(Double.parseDouble(maxX0.getText()), Double.parseDouble(maxX1.getText()));
    }
}
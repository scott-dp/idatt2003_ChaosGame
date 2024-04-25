package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.Complex;
import edu.ntnu.stud.models.JuliaTransform;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
public class AddJuliaTransformView {
    private final ChaosGameController chaosGameController = AppView.getChaosGameController();

    TextField imaginaryPartField;
    TextField realPartField;

    //Elements minCoords
    private TextField minX0;
    private TextField minX1;

    //Elements maxCoords
    private TextField maxX0;
    private TextField maxX1;

    private final Stage stage = new Stage();
    private final Scene scene = new Scene(new VBox());

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
     * Method that sets the scene of the stage.
     */
    public void setScene() {
        this.scene.setRoot(setMainLayout());
    }

    /**
     * Method that sets the main layout of the Interface.
     * The layout consists of a vertical container that contains the top row, bottom row and a save button.
     *
     * @return mainLayout the main layout container of the interface
     */
    public VBox setMainLayout() {
        VBox mainLayout = new VBox();

        mainLayout.getChildren().addAll(topRowContainer(), bottomRowContainer(), saveButton());

        return mainLayout;
    }

    /**
     * Method that creates the top row container.
     * The top container consists of a label and a text field for the real part of the complex number.
     * There is an extra vertical container that contains two text fields for the min coordinates.
     * This container is then added to the horizontal container.
     * @return topRowContainer the container for the real part of the complex number and the min coordinates
     */
    public HBox topRowContainer() {
        HBox topRowContainer = new HBox(10);

        Label realPartLabel = new Label("Real part:");
        realPartField = new TextField("Enter Real Part");

        Label minCoordsLabel = new Label("Min Coords:");

        VBox minCoordsContainer = new VBox(10);
        minX0 = new TextField("0");
        minX1 = new TextField("0");
        minCoordsContainer.getChildren().addAll(minX0, minX1);

        topRowContainer.getChildren().addAll(realPartLabel, realPartField, minCoordsLabel, minCoordsContainer);

        return topRowContainer;
    }

    /**
     * Method that creates the bottom row container.
     * The bottom container consists of a label and a text field for the imaginary part of the complex number.
     * There is an extra vertical container that contains two text fields for the max coordinates.
     * This container is then added to the horizontal container.
     * @return bottomRowContainer the container for the imaginary part of the complex number and the max coordinates
     */
    public HBox bottomRowContainer() {
        HBox bottomRowContainer = new HBox(10);

        Label imaginaryPartLabel = new Label("Imaginary part:");
        imaginaryPartField = new TextField("Enter Imaginary Part");

        Label maxCoordsLabel = new Label("Max Coords:");

        VBox maxCoordsContainer = new VBox(10);
        maxX0 = new TextField("0");
        maxX1 = new TextField("0");
        maxCoordsContainer.getChildren().addAll(maxX0, maxX1);

        bottomRowContainer.getChildren().addAll(imaginaryPartLabel, imaginaryPartField, maxCoordsLabel, maxCoordsContainer);

        return bottomRowContainer;
    }

    /**
     *
     * @return
     */
    public Button saveButton() {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if (!isInputValid()) {
                ChaosGameUtils.showErrorAlert("Input is invalid");
                return;
            }
            chaosGameController.setChaosGame(createJuliaDescription());
            stage.close();
        });
        return saveButton;
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

    public boolean isInputValid() {
        try {
            Double.parseDouble(realPartField.getText());
            Double.parseDouble(imaginaryPartField.getText());
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
            ChaosGameUtils.showErrorAlert(e.getMessage());
            return false;
        }
    }
}
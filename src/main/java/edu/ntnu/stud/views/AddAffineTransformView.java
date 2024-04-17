package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class AddAffineTransformView {
    private final ChaosGameController chaosGameController = AppView.getChaosGameController();

    private final ArrayList<Transform2D> affineTransforms = new ArrayList<>();

    private final Stage stage = new Stage();
    private final Scene scene = new Scene(new VBox());

    //Elements in matrix
    TextField a0;
    TextField a1;
    TextField b0;
    TextField b1;

    //Elements in Vector
    TextField x0;
    TextField x1;

    //Elements minCoords
    private TextField minX0;
    private TextField minX1;

    //Elements maxCoords
    private TextField maxX0;
    private TextField maxX1;

    public void setScene(){
        this.scene.setRoot(setMainLayout());
    }

    public VBox setMainLayout(){
        VBox mainLayout = new VBox(10);

        VBox matrixVectors = new VBox(10);
        matrixVectors.getChildren().addAll(enterMatrix(),enterVector());

        VBox coordinatesInput = new VBox(10);

        coordinatesInput.getChildren().addAll(enterMinCoords(), enterMaxCoords());

        HBox mainHorizontalContainer = new HBox(10);
        mainHorizontalContainer.getChildren().addAll(matrixVectors, coordinatesInput,addTransformButton(),saveButton());

        mainLayout.getChildren().add(mainHorizontalContainer);
        return mainLayout;
    }

    public HBox enterMatrix(){
        HBox matrixHorizontal = new HBox(10);
        Label matrixLabel = new Label("Matrix: ");
        matrixHorizontal.getChildren().add(matrixLabel);
        VBox matrixLayout = new VBox();


        HBox topNumbersContainer = new HBox(10);
        a0 = new TextField("0");
        a1 = new TextField("0");
        topNumbersContainer.getChildren().addAll(a0, a1);

        HBox bottomNumbersContainer = new HBox(10);
        b0 = new TextField("0");
        b1 = new TextField("0");
        bottomNumbersContainer.getChildren().addAll(b0, b1);

        matrixLayout.getChildren().addAll(topNumbersContainer, bottomNumbersContainer);

        matrixHorizontal.getChildren().add(matrixLayout);
        return matrixHorizontal;
    }

    public HBox enterVector() {
        HBox vectorHorizontalContainer = new HBox(10);
        Label vectorLabel = new Label("Vector: ");

        vectorHorizontalContainer.getChildren().add(vectorLabel);
        VBox vectorLayout = new VBox(10);
        x0 = new TextField("0");
        x1 = new TextField("0");
        vectorLayout.getChildren().addAll(x0, x1);

        vectorHorizontalContainer.getChildren().add(vectorLayout);

        return vectorHorizontalContainer;
    }

    public HBox enterMinCoords(){
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

    public HBox enterMaxCoords(){
        HBox maxCoordsContainer = new HBox(10);
        Label maxCoordsLabel = new Label("Max Coords: ");
        maxCoordsContainer.getChildren().add(maxCoordsLabel);
        VBox minCoordsLayout = new VBox(10);

        maxX0 = new TextField("0");
        maxX1 = new TextField("0");

        minCoordsLayout.getChildren().addAll(maxX0, maxX1);

        maxCoordsContainer.getChildren().add(minCoordsLayout);
        return maxCoordsContainer;
    }

    public Button addTransformButton(){
        Button addTransformButton = new Button("Add Affine Transform");
        addTransformButton.setOnAction(e -> {
            createTransformation();
            clearTextFields();
            showAlert();
        });
        return addTransformButton;
    }

    public Button saveButton(){
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            chaosGameController.setChaosGame(new ChaosGameDescription(getMinCoords(), getMaxCoords(), getTransformList()));
            stage.close();
        });
        return saveButton;
    }

    public void showStage(){
        setScene();
        stage.setScene(scene);
        stage.show();
    }

    public void createTransformation(){
        AffineTransform2D newTransformation;
        double a = Double.parseDouble(a0.getText());
        double b = Double.parseDouble(a1.getText());
        double c = Double.parseDouble(b0.getText());
        double d = Double.parseDouble(b1.getText());

        Matrix2x2 newMatrix = new Matrix2x2(a,b,c,d);

        double x = Double.parseDouble(x0.getText());
        double y = Double.parseDouble(x1.getText());

        Vector2D newVector = new Vector2D(x,y);

        newTransformation = new AffineTransform2D(newMatrix, newVector);

        affineTransforms.add(newTransformation);
    }

    public List<Transform2D> getTransformList(){
        return affineTransforms;
    }

    public Vector2D getMinCoords(){
        return new Vector2D(Double.parseDouble(minX0.getText()),Double.parseDouble(minX1.getText()));
    }

    public Vector2D getMaxCoords(){
        return new Vector2D(Double.parseDouble(maxX0.getText()),Double.parseDouble(maxX1.getText()));
    }

    public void clearTextFields() {
        a0.clear();
        a1.clear();
        b0.clear();
        b1.clear();
        x0.clear();
        x1.clear();
        minX0.setDisable(true);
        minX1.setDisable(true);
        maxX0.setDisable(true);
        maxX1.setDisable(true);
    }

    private void showAlert() {
        // Create an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null); // No header
        alert.setContentText("Transformation added");

        // Display the alert and wait for it to be dismissed
        alert.showAndWait();
    }
}

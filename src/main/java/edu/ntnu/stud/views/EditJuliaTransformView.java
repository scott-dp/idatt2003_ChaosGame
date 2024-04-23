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

public class EditJuliaTransformView {
  private final ChaosGameController chaosGameController = AppView.getChaosGameController();
  private final Stage stage;
  private final Scene scene;
  TextField imaginaryPartField;
  TextField realPartField;
  private TextField minX0;
  private TextField minX1;

  //Elements maxCoords
  private TextField maxX0;
  private TextField maxX1;

  private final JuliaTransform currentTransform;

  public EditJuliaTransformView() {
    currentTransform = (JuliaTransform) chaosGameController.getChaosGame().getDescription().getTransforms().get(0);
    stage = new Stage();
    scene = new Scene(new VBox());
  }

  public void showStage() {
    setScene();
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }

  public void setScene() {
    this.scene.setRoot(setMainLayout());
  }

  public VBox setMainLayout() {
    VBox mainLayout = new VBox();
    mainLayout.getChildren().addAll(topRowContainer(), bottomRowContainer(), saveButton());

    return mainLayout;
  }

  public HBox topRowContainer() {
    HBox topRowContainer = new HBox(10);

    Label realPartLabel = new Label("Real part:");
    realPartField = new TextField(String.valueOf(currentTransform.getConstantPoint().getX0()));

    Label minCoordsLabel = new Label("Min Coords:");

    VBox minCoordsContainer = new VBox(10);
    minX0 = new TextField(String.valueOf(chaosGameController.getChaosGame().getDescription().getMinCoords().getX0()));
    minX1 = new TextField(String.valueOf(chaosGameController.getChaosGame().getDescription().getMinCoords().getX1()));
    minCoordsContainer.getChildren().addAll(minX0, minX1);

    topRowContainer.getChildren().addAll(realPartLabel, realPartField, minCoordsLabel, minCoordsContainer);

    return topRowContainer;
  }

  public HBox bottomRowContainer() {
    HBox bottomRowContainer = new HBox(10);

    Label imaginaryPartLabel = new Label("Imaginary part:");
    imaginaryPartField = new TextField(String.valueOf(currentTransform.getConstantPoint().getX1()));

    Label maxCoordsLabel = new Label("Max Coords:");

    VBox maxCoordsContainer = new VBox(10);
    maxX0 = new TextField(String.valueOf(chaosGameController.getChaosGame().getDescription().getMaxCoords().getX0()));
    maxX1 = new TextField(String.valueOf(chaosGameController.getChaosGame().getDescription().getMaxCoords().getX1()));
    maxCoordsContainer.getChildren().addAll(maxX0, maxX1);

    bottomRowContainer.getChildren().addAll(imaginaryPartLabel, imaginaryPartField, maxCoordsLabel, maxCoordsContainer);

    return bottomRowContainer;
  }

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

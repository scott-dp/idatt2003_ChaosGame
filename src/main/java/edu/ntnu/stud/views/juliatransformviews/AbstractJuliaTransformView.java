package edu.ntnu.stud.views.juliatransformviews;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.Coordinate;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.AppView;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractJuliaTransformView {
    protected final ChaosGameController chaosGameController;
    protected final Stage stage;
    protected final Scene scene;

    protected TextField imaginaryPartField;
    protected TextField realPartField;

    protected TextField minX0;
    protected TextField minX1;

    protected TextField maxX0;
    protected TextField maxX1;

    protected AbstractJuliaTransformView() {
        chaosGameController = AppView.getChaosGameController();
        this.stage = new Stage();
        this.scene = new Scene(new VBox());
        imaginaryPartField = new TextField();
        realPartField = new TextField();
        minX0 = new TextField();
        minX1 = new TextField();
        maxX0 = new TextField();
        maxX1 = new TextField();
    }

    public abstract void setScene();

    public Vector2D getMinCoords() {
        return new Vector2D(Double.parseDouble(minX0.getText()), Double.parseDouble(minX1.getText()));
    }

    public Vector2D getMaxCoords() {
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

  public void showStage() {
      setScene();
      stage.setScene(scene);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.show();
  }

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

  public abstract void saveButtonAction(ActionEvent actionEvent);

  public Button createSaveButton() {
    Button saveButton = new Button("Save");
    saveButton.setOnAction(this::saveButtonAction);
    return saveButton;
  }

  public VBox createNumeralInput() {
    VBox numeralContainer = new VBox(10);
    HBox realPartContainer = new HBox(10);
    Label realPartLabel = new Label("Real part:");
    realPartField = new TextField("Enter Real Part");
    realPartContainer.getChildren().addAll(realPartLabel, realPartField);

    HBox imaginaryPartContainer = new HBox(10);
    Label imaginaryPartLabel = new Label("Imaginary part:");
    imaginaryPartField = new TextField("Enter Imaginary Part");
    imaginaryPartContainer.getChildren().addAll(imaginaryPartLabel, imaginaryPartField);

    numeralContainer.getChildren().addAll(realPartContainer, imaginaryPartContainer);
    return numeralContainer;
  }

  public abstract VBox setMainLayout();


  public VBox getCoordinatesInput() {
    VBox coordinatesInput = new VBox(10);

    coordinatesInput.getChildren().addAll(createCoordinateInput(minX0, minX1, Coordinate.MIN),
            createCoordinateInput(maxX0, maxX1, Coordinate.MAX));
    return coordinatesInput;
  }
}

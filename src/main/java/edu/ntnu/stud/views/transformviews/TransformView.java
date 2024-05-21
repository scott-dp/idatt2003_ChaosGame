package edu.ntnu.stud.views.transformviews;

import edu.ntnu.stud.models.Coordinate;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * An abstract class that represents a view in the app that has to do with transforms.
 */
public abstract class TransformView {
  protected final Stage stage;
  protected final Scene scene;
  protected TextField minX0;
  protected TextField minX1;

  protected TextField maxX0;
  protected TextField maxX1;

  /**
   * Constructor for the TransformView class.
   */
  public TransformView() {
    this.stage = new Stage();
    this.scene = new Scene(new VBox());
    minX0 = new TextField();
    minX1 = new TextField();
    maxX0 = new TextField();
    maxX1 = new TextField();
  }

  /**
   * A method that creates a horizontal container for entering the max coordinates of the fractal.
   * The max coordinates layout consists of a horizontal container, within a vertical container.
   * The horizontal container holds text-fields which represent the max coordinates of the fractal.
   *
   * @return HBox maxCoordsContainer
   */
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

  /**
   * Method that validates the min and max coordinates of the fractal.
   */
  public void validateCoords() {
    try {
      double minX0parsed = Double.parseDouble(minX0.getText());
      double minX1parsed = Double.parseDouble(minX1.getText());
      double maxX0parsed = Double.parseDouble(maxX0.getText());
      double maxX1parsed = Double.parseDouble(maxX1.getText());
      ChaosGameUtils.validateMinAndMaxCoords(
          new Vector2D(minX0parsed, minX1parsed), new Vector2D(maxX0parsed, maxX1parsed));
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Coordinates must be numbers.");
    }
  }
}


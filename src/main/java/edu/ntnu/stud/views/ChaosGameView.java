package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.observer.ChaosGameObserver;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

/**
 * View class for the ChaosGame class. Implements the {@link ChaosGameObserver} interface.
 */
public class ChaosGameView implements ChaosGameObserver {
  private final ChaosGame game;
  private final Canvas canvas;
  private final CheckBox colorViewCheckBox;

  /**
   * Constructs an instance of ChaosGameView with a set ChaosGame.
   *
   * @param game the ChaosGame object being shown
   */
  public ChaosGameView(ChaosGame game) {
    this.game = game;
    this.canvas = new Canvas(game.getChaosCanvas().getWidth(), game.getChaosCanvas().getHeight());
    this.colorViewCheckBox = new CheckBox("Enable color view");

    colorViewCheckBox.selectedProperty().addListener(observable -> makeFractal());
  }

  public CheckBox getColorViewCheckBox() {
    return this.colorViewCheckBox;
  }

  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * Adds the fractal to a {@link GraphicsContext} object.
   */
  public void makeFractal() {
    int[][] fractalList = game.getChaosCanvas().getCanvas();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    clearGraphicsContext();

    if (colorViewCheckBox.isSelected()) {
      int[] minAndMax = findMinAndMaxArrayValue(fractalList);
      int min = minAndMax[0];
      int max = minAndMax[1];
      System.out.println(min);
      System.out.println(max);
      for (int i = 0; i < fractalList.length; i++) {
        for (int j = 0; j < fractalList[i].length; j++) {
          int val = fractalList[i][j];
          if (val == 0) {
            continue;
          } else if (val == max) {
            gc.setFill(Color.VIOLET);
          } else if (val == min) {
            gc.setFill(Color.RED);
          } else if (scale(min, max, val) <= 0.1) {
            gc.setFill(Color.ORANGE);
          } else if(scale(min, max, val) <= 0.25) {
            gc.setFill(Color.YELLOW);
          } else if(scale(min, max, val) <= 0.5) {
            gc.setFill(Color.GREEN);
          } else if(scale(min, max, val) <= 0.75) {
            gc.setFill(Color.BLUE);
          } else {
            gc.setFill(Color.INDIGO);
          }
          gc.fillRect(j, i, 1, 1);
        }
      }
    } else {
      for (int i = 0; i < fractalList.length; i++) {
        for (int j = 0; j < fractalList[i].length; j++) {
          if (fractalList[i][j] != 0) {
            gc.setFill(Color.BLACK);
            gc.fillRect(j, i, 1, 1);
          }
        }
      }
    }
  }

  public double scale(int min, int max, int val) {
    max = max - min;
    val = val - min;
    double maxDouble = max;
    double valDouble = val;

    return valDouble/maxDouble;
  }

  public int[] findMinAndMaxArrayValue(int[][] fractalList) {
    int max = fractalList[0][0];
    int min = max;

    for (int[] row : fractalList) {
      for (int val : row) {
        if (val > max) {
          max = val;
        } else if (val < min) {
          min = val;
        }
      }
    }

    if (min != max) {
      min = 1;
    }

    return new int[]{min, max};
  }


  /**
   * Clears the {@link GraphicsContext} object.
   */
  public void clearGraphicsContext() {
    int[][] fractalList = game.getChaosCanvas().getCanvas();
    GraphicsContext gc = canvas.getGraphicsContext2D();

    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        gc.clearRect(j, i, 1, 1);
      }
    }
  }

  /**
   * Updates the fractal. Is called when a change has been made in the ChaosGame
   * object that this object is observing.
   */
  @Override
  public void update() {
    makeFractal();
  }
}

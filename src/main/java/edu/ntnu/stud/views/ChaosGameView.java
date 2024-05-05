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
      for (int i = 0; i < fractalList.length; i++) {
        for (int j = 0; j < fractalList[i].length; j++) {
          //Dette ga opphav til noen kule fraktaler
          // (lar default ikke være med med vilje)
          switch (fractalList[i][j]){
            case 0:
              continue;
            case 1:
              gc.setFill(Color.rgb(0, 255, 0));
              break;
            case 2:
              gc.setFill(Color.rgb(0, 0, 235));
              break;
            case 3:
              gc.setFill(Color.rgb(255, 0, 0));
              break;
            case 4:
              gc.setFill(Color.rgb(255, 255, 0));
              break;
            case 5:
              gc.setFill(Color.rgb(0, 255, 255));
              break;
            case 6:
              gc.setFill(Color.rgb(255, 0, 255));
              break;
            case 7:
              gc.setFill(Color.rgb(125, 255, 200));
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

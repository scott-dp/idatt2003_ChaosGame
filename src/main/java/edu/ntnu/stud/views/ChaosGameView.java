package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.observer.ChaosGameObserver;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * View class for the ChaosGame class. Implements the {@link ChaosGameObserver} interface.
 */
public class ChaosGameView implements ChaosGameObserver {
  private final ChaosGame game;
  Canvas canvas;

  /**
   * Constructs an instance of ChaosGameView with a set ChaosGame.
   *
   * @param game the ChaosGame object being shown
   */
  public ChaosGameView(ChaosGame game) {
    this.game = game;
    this.canvas = new Canvas(game.getChaosCanvas().getWidth(), game.getChaosCanvas().getHeight());
  }

  public ChaosGame getGame() {
    return game;
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
    gc.setFill(Color.BLACK);
    clearGraphicsContext();

    //TODO streams lamba
    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        if (fractalList[i][j] == 1) {
          gc.fillRect(j, i, 1, 1);
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

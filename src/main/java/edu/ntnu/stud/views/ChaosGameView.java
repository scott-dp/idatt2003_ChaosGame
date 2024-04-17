package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.observer.ChaosGameObserver;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * View class to show
 */
public class ChaosGameView implements ChaosGameObserver {
  private final ChaosGame game;
  Canvas canvas;

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

  public void clearGraphicsContext() {
    int[][] fractalList = game.getChaosCanvas().getCanvas();
    GraphicsContext gc = canvas.getGraphicsContext2D();

    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        gc.clearRect(j, i, 1, 1);
      }
    }
  }

  @Override
  public void update() {
    makeFractal();
  }
}

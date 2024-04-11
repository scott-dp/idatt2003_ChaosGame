package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosCanvas;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChaosGameView {
  ChaosGame game;


  public ChaosGameView(ChaosGame game) {
    this.game = game;
  }

  public void showFractal(Stage stage) {
    ChaosCanvas fractalCanvas = game.getCanvas();
    int[][] fractalList = fractalCanvas.getCanvas();
    Canvas canvas = new Canvas(fractalCanvas.getWidth(), fractalCanvas.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);

    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        gc.fillRect(i, j, 1, 1);
      }
    }

  }
}

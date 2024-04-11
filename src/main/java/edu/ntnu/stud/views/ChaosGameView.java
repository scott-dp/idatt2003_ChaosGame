package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosCanvas;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChaosGameView {
  private final ChaosGame game;


  public ChaosGameView(ChaosGame game) {
    this.game = game;
  }

  public ChaosGame getGame() {
    return game;
  }

  public void showFractal(StackPane layout) {
    ChaosCanvas fractalCanvas = game.getCanvas();
    int[][] fractalList = fractalCanvas.getCanvas();
    Canvas canvas = new Canvas(fractalCanvas.getWidth(), fractalCanvas.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);

    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        if (fractalList[i][j] == 1) {
          gc.fillRect(j, i, 1, 1);
        }
      }
    }

    layout.getChildren().add(canvas);
  }
}

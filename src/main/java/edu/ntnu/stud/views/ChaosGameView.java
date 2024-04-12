package edu.ntnu.stud.views;

import edu.ntnu.stud.models.chaosgamehandling.ChaosCanvas;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.interfaces.ChaosGameObserver;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChaosGameView implements ChaosGameObserver {
  private final ChaosGame game;
  VBox mainLayout;

  public ChaosGameView(ChaosGame game, VBox mainLayout) {
    this.game = game;
    this.mainLayout = mainLayout;
  }

  public ChaosGame getGame() {
    return game;
  }

  public VBox getMainLayout() {
    return mainLayout;
  }

  public Canvas makeFractal() {
    game.runSteps(10000);
    ChaosCanvas fractalCanvas = game.getCanvas();
    int[][] fractalList = fractalCanvas.getCanvas();
    Canvas canvas = new Canvas(fractalCanvas.getWidth(), fractalCanvas.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);

    //TODO streams lamba
    for (int i = 0; i < fractalList.length; i++) {
      for (int j = 0; j < fractalList[i].length; j++) {
        if (fractalList[i][j] == 1) {
          gc.fillRect(j, i, 1, 1);
        }
      }
    }

    return canvas;
  }

  public void drawLayout (Canvas fractal) {
    mainLayout.getChildren().add(fractal);
  }

  @Override
  public void update() {
    drawLayout(makeFractal());
  }
}

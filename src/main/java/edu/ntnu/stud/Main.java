package edu.ntnu.stud;

import edu.ntnu.stud.models.*;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.ui.UserInterface;
import edu.ntnu.stud.views.ChaosGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ChaosGame sierpinskiGame = new ChaosGame(ChaosGameDescriptionFactory.createSierpinskiDescription(), 400, (int) (400*0.86));
    ChaosGameView gameView = new ChaosGameView(sierpinskiGame);
    gameView.getGame().runSteps(1000000);

    StackPane layout = new StackPane();
    gameView.showFractal(layout);

    Scene scene = new Scene(layout, 400, 400);
    stage.setScene(scene);
    stage.show();
  }
}

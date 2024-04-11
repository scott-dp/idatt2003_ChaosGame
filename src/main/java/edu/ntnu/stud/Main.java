package edu.ntnu.stud;

import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.Complex;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.views.ChaosGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ChaosGame sierpinskiGame = new ChaosGame(ChaosGameDescriptionFactory.createJuliaSet(new Complex(-.74543, .11301)), 400, 400);
    ChaosGameView gameView = new ChaosGameView(sierpinskiGame);
    gameView.getGame().runSteps(1000000);

    StackPane layout = new StackPane();
    gameView.showFractal(layout);

    Scene scene = new Scene(layout, 400, 400);
    stage.setScene(scene);
    stage.show();
  }
}

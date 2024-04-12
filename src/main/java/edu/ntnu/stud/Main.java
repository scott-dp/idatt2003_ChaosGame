package edu.ntnu.stud;

import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.views.ChaosGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ChaosGameView gameView = new ChaosGameView(new ChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription2(), 400, 400), new VBox());
    gameView.update();
    stage.setScene(new Scene(gameView.getMainLayout(), 400, 400));
    stage.show();
  }
}

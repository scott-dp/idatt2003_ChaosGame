package edu.ntnu.stud.views;

import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppView extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    ChaosGameView gameView = new ChaosGameView(new ChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription2(), 400, 400), new VBox());
    gameView.update();
    stage.setScene(new Scene(gameView.getMainLayout(), 400, 400));
    stage.show();
  }

  public void launchApp() {
    launch();
  }
}

package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppView extends Application {
  private ChaosGameController chaosGameController;
  private Slider slider;
  MenuBar menuBar;
  VBox mainLayout;
  HBox bottomLayout;
  Button runButton;

  @Override
  public void start(Stage stage) throws Exception {
    ChaosGame game = new ChaosGame(ChaosGameDescriptionFactory.createBarnsleyDescription(), 450, 450);
    ChaosGameView gameView = new ChaosGameView(game);

    chaosGameController = new ChaosGameController(game, gameView);

    game.addObserver(gameView);
    gameView.update();

    createSlider();
    createMenuBar();
    createRunButton();
    createBottomLayout();
    setMainLayout();

    stage.setScene(new Scene(mainLayout, 450, 450));
    stage.show();
  }

  public void setMainLayout() {
    mainLayout = new VBox();
    mainLayout.getChildren().add(menuBar);
    mainLayout.getChildren().add(chaosGameController.getChaosGameView().getCanvas());
    mainLayout.getChildren().add(bottomLayout);
  }

  public void createSlider() {
    slider = new Slider();
    slider.setMin(0);
    slider.setMax(100000);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(10000);
  }

  public void createMenuBar() {
    menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu predefinedMenu = new Menu("Predefined");
    Menu editMenu = new Menu("Edit");
    menuBar.getMenus().addAll(fileMenu, predefinedMenu, editMenu);
  }

  public void createBottomLayout() {
    bottomLayout = new HBox(10);
    bottomLayout.getChildren().add(slider);
    bottomLayout.getChildren().add(runButton);
  }

  public void createRunButton(){
    runButton = new Button("Run");
    runButton.setOnAction(e -> chaosGameController.runSteps((int) slider.getValue()));
  }

  public void launchApp(String[] args) {
    launch(args);
  }
}

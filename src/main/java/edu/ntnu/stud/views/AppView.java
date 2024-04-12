package edu.ntnu.stud.views;

import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppView extends Application {
  private ChaosGame game;
  private ChaosGameView gameView;
  private Slider slider;
  VBox mainLayout = new VBox();

  @Override
  public void start(Stage stage) throws Exception {
    game = new ChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription2(), 400, 400);
    gameView = new ChaosGameView(game);

    game.addObserver(gameView);

    gameView.update();

    slider = new Slider();
    slider.setMin(0);
    slider.setMax(100000);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(10000);

    setMainLayout();

    stage.setScene(new Scene(getMainLayout(), 400, 450));
    stage.show();
  }

  public void setMainLayout() {
    mainLayout = new VBox();
    mainLayout.getChildren().add(createMenuBar());
    mainLayout.getChildren().add(gameView.getCanvas());
    mainLayout.getChildren().add(bottomLayout());
  }

  public VBox getMainLayout() {
    return mainLayout;
  }

  public MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu predefinedMenu = new Menu("Predefined");
    Menu editMenu = new Menu("Edit");
    menuBar.getMenus().addAll(fileMenu, predefinedMenu, editMenu);
    return menuBar;
  }

  public HBox bottomLayout() {
    HBox bottomLayout = new HBox(10);
    bottomLayout.getChildren().add(slider);
    bottomLayout.getChildren().add(runButton());
    return bottomLayout;
  }

  public Button runButton(){
    Button runButton = new Button("Run");
    runButton.setOnAction(e -> game.runSteps((int) slider.getValue()));
    return runButton;
  }

  public void launchApp(String[] args) {
    launch(args);
  }
}

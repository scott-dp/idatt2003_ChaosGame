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

  @Override
  public void start(Stage stage) throws Exception {
    game = new ChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription2(), 400, 400);
    gameView = new ChaosGameView(game, new VBox());

    // Assuming ChaosGame has a method to add observers:
    game.addObserver(gameView);

    gameView.update();
    BorderPane root = new BorderPane();

    // Set the menu bar and the main game layout in the border pane
    root.setTop(createMenuBar());
    root.setCenter(gameView.getMainLayout()); // Changed to setCenter

    // You might want to set a larger scene size if the menu bar is not showing correctly
    stage.setScene(new Scene(getMainLayout(), 400, 450));
    stage.show();
  }

  public VBox getMainLayout() {
    VBox mainLayout = new VBox();
    mainLayout.getChildren().add(createMenuBar());
    mainLayout.getChildren().add(gameView.getMainLayout());
    mainLayout.getChildren().add(bottomLayout());
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
    bottomLayout.getChildren().add(runStepsSlider());
    bottomLayout.getChildren().add(runButton());
    return bottomLayout;
  }

  public Slider runStepsSlider() {
    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(10000);
    slider.setValue(10000);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(10000);
    slider.setMinorTickCount(1000);
    slider.setBlockIncrement(1000);
    return slider;
  }

  public Button runButton(){
    Button runButton = new Button("Run");
    runButton.setOnAction(e -> game.runSteps((int) runStepsSlider().getValue()));
    return runButton;
  }

  public void launchApp() {
    launch();
  }
}

package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    ChaosGame game = new ChaosGame(ChaosGameDescriptionFactory.createSierpinskiDescription(),
        450, 450);
    ChaosGameView gameView = new ChaosGameView(game);

    chaosGameController = new ChaosGameController(game, gameView);
    chaosGameController.addObserver(gameView);

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
    Menu emptyFractalMenu = new Menu("New Empty Fractal");
    Menu predefinedMenu = new Menu("Predefined Fractal");
    createPredefinedMenu(predefinedMenu);
    Menu editMenu = new Menu("Edit Current fractal");
    menuBar.getMenus().addAll(fileMenu, emptyFractalMenu, predefinedMenu, editMenu);
  }

  public void createPredefinedMenu(Menu predefinedMenu) {
    MenuItem barnsleyFernItem = new MenuItem("Barnsley Fern");
    barnsleyFernItem.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.createBarnsleyDescription()));

    MenuItem sierpinskiTriangleItem = new MenuItem("Sierpinski Triangle");
    sierpinskiTriangleItem.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.createSierpinskiDescription()));

    MenuItem juliaSet1Item = new MenuItem("Julia set 1");
    juliaSet1Item.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription1()));

    MenuItem juliaSet2Item = new MenuItem("Julia set 2");
    juliaSet2Item.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription2()));

    MenuItem juliaSet3Item = new MenuItem("Julia set 3");
    juliaSet3Item.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription3()));

    MenuItem juliaSet4Item = new MenuItem("Julia set 4");
    juliaSet4Item.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.getJuliaSetDescription4()));

    predefinedMenu.getItems().addAll(barnsleyFernItem, sierpinskiTriangleItem,
        juliaSet1Item, juliaSet2Item, juliaSet3Item, juliaSet4Item);
  }

  public void createBottomLayout() {
    bottomLayout = new HBox(10);
    bottomLayout.getChildren().add(slider);
    bottomLayout.getChildren().add(runButton);
  }

  public void createRunButton() {
    runButton = new Button("Run");
    runButton.setOnAction(e -> chaosGameController.runSteps((int) slider.getValue()));
  }

  public void launchApp(String[] args) {
    launch(args);
  }
}

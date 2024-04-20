package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.JuliaTransform;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameFileHandler;
import edu.ntnu.stud.models.exceptions.EmptyFileException;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The main view class for the ChaosGame application.
 */
public class AppView extends Application {
  private static ChaosGameController chaosGameController;
  private ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
  private Slider slider;
  MenuBar menuBar;
  VBox mainLayout;
  HBox bottomLayout;
  Button runButton;

  /**
   * Initializes objects and layout and starts the application.
   *
   * @param stage the stage to be shown
   * @throws Exception if the application cannot be started
   */
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

  /**
   * Sets the main layout of the application.
   */
  public void setMainLayout() {
    mainLayout = new VBox();
    mainLayout.getChildren().add(menuBar);
    mainLayout.getChildren().add(chaosGameController.getChaosGameView().getCanvas());
    mainLayout.getChildren().add(bottomLayout);
  }

  /**
   * Creates the slider used to control the number of steps in the chaos game.
   */
  public void createSlider() {
    slider = new Slider();
    slider.setMin(0);
    slider.setMax(100000);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(10000);
  }

  /**
   * Creates the menu bar for the application and adds menu items to it.
   */
  public void createMenuBar() {
    menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    createFileMenu(fileMenu);
    Menu emptyFractalMenu = new Menu("New Empty Fractal");
    createEmptyFractalMenu(emptyFractalMenu);
    Menu predefinedMenu = new Menu("Predefined Fractal");
    createPredefinedMenu(predefinedMenu);
    Menu editMenu = new Menu("Edit");
    //TODO make own method for this below
    MenuItem editCurrentFractalItem = new MenuItem("Edit current fractal");
    editCurrentFractalItem.setOnAction(this::editMenuAction);
    editMenu.getItems().add(editCurrentFractalItem);
    menuBar.getMenus().addAll(fileMenu, emptyFractalMenu, predefinedMenu, editMenu);
  }

  public void editMenuAction(ActionEvent actionEvent) {
    if (chaosGameController.getChaosGame().getDescription().getTransforms().get(0).getClass() == JuliaTransform.class) {
      EditJuliaTransformView editJuliaTransformView = new EditJuliaTransformView();
      editJuliaTransformView.showStage();
    } else if (chaosGameController.getChaosGame().getDescription().getTransforms().get(0).getClass() == AffineTransform2D.class) {
      EditAffineTransformView editAffineTransformView = new EditAffineTransformView();
      editAffineTransformView.showStage();
    } else {
      ChaosGameUtils.showErrorAlert("No fractal chosen");
    }
  }

  public void createFileMenu(Menu fileMenu) {
    MenuItem saveFractal = new MenuItem("Save fractal");
    MenuItem loadFractal = new MenuItem("Load fractal from file");

    saveFractal.setOnAction(this::saveFractalToFileAction);
    loadFractal.setOnAction(this::loadFractalFromFileAction);

    fileMenu.getItems().addAll(saveFractal, loadFractal);
  }

  public void saveFractalToFileAction(ActionEvent actionEvent) {
    SaveFileView saveFileView = new SaveFileView();
    String fileName = saveFileView.getFileNameFromUser();
    try {
      fileHandler.writeToFile(chaosGameController.getChaosGame().getDescription(),
          saveFileView.getChosenDirectory().concat("/" + fileName + ".txt"));
    } catch (IOException e) {
      ChaosGameUtils.showErrorAlert(e.getMessage());
    } catch (NullPointerException e) {
      ChaosGameUtils.showErrorAlert("No directory chosen");
    }
  }

  public void loadFractalFromFileAction(ActionEvent actionEvent) {
    LoadFileView loadFileView = new LoadFileView();
    try {
      chaosGameController.setChaosGame(fileHandler.readFromFile(loadFileView.getChosenFilePath()));
    } catch (FileNotFoundException | NoSuchElementException | EmptyFileException e) {
      ChaosGameUtils.showErrorAlert(e.getMessage());
    } catch (NullPointerException e) {
      ChaosGameUtils.showErrorAlert("No file chosen");
    }
  }

  /**
   * Creates the {@link MenuItem} objects for adding and creating empty Affine and Julia fractals.
   *
   * @param emptyFractalMenu the menu where the menu items are added
   */
  public void createEmptyFractalMenu(Menu emptyFractalMenu) {
    MenuItem affineTransformItem = new MenuItem("Affine Transform");
    MenuItem juliaTransformItem = new MenuItem("Julia transform");
    affineTransformItem.setOnAction(event -> {
      AddAffineTransformView affineTransformView = new AddAffineTransformView();
      affineTransformView.showStage();
    });
    juliaTransformItem.setOnAction(event -> {
      AddJuliaTransformView juliaTransformView = new AddJuliaTransformView();
      juliaTransformView.showStage();
    });
    emptyFractalMenu.getItems().addAll(affineTransformItem, juliaTransformItem);
  }

  /**
   * Creates the {@link MenuItem} objects for adding and creating predefined fractals.
   *
   * @param predefinedMenu the menu where the menu items are added
   */
  public void createPredefinedMenu(Menu predefinedMenu) {
    MenuItem barnsleyFernItem = new MenuItem("Barnsley Fern");
    barnsleyFernItem.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(ChaosGameDescriptionFactory.createBarnsleyDescription()));

    MenuItem sierpinskiTriangleItem = new MenuItem("Sierpinski Triangle");
    sierpinskiTriangleItem.setOnAction(actionEvent ->
        chaosGameController.setChaosGame(
            ChaosGameDescriptionFactory.createSierpinskiDescription()));

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

  /**
   * Creates the bottom layout of the application containing slider to choose amount of steps
   * and run button.
   */
  public void createBottomLayout() {
    bottomLayout = new HBox(10);
    bottomLayout.getChildren().add(slider);
    bottomLayout.getChildren().add(runButton);
  }

  /**
   * Creates the run button and sets the action to run the chaos game for the amount of steps
   * specified by the slider.
   */
  public void createRunButton() {
    runButton = new Button("Run");
    runButton.setOnAction(this::runButtonAction);
  }

  public void runButtonAction(ActionEvent actionEvent) {
    try {
      chaosGameController.runSteps((int) slider.getValue());
    } catch (NumberFormatException e) {
      ChaosGameUtils.showErrorAlert("Couldn't generate fractal out " +
          "of the given transforms because the point did not converge");
    }
  }

  /**
   * Launches the application.
   *
   * @param args the command line arguments
   */
  public void launchApp(String[] args) {
    launch(args);
  }

  public static ChaosGameController getChaosGameController() {
    return chaosGameController;
  }
}

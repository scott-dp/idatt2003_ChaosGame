package edu.ntnu.stud.views;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.controllers.ChaosGameControllerSingleton;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGame;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameFileHandler;
import edu.ntnu.stud.models.exceptions.EmptyFileException;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.affinetransformviews.AbstractAffineTransformView;
import edu.ntnu.stud.views.affinetransformviews.AddAffineTransformView;
import edu.ntnu.stud.views.affinetransformviews.EditAffineTransformView;
import edu.ntnu.stud.views.fileviews.LoadFileView;
import edu.ntnu.stud.views.fileviews.SaveFileView;
import edu.ntnu.stud.views.juliatransformviews.AbstractJuliaTransformView;
import edu.ntnu.stud.views.juliatransformviews.AddJuliaTransformView;
import edu.ntnu.stud.views.juliatransformviews.EditJuliaTransformView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * The main view class for the ChaosGame application.
 */
public class AppView extends Application {
  private final ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
  private Slider slider;
  MenuBar menuBar;
  VBox mainLayout;
  HBox bottomLayout;
  Button runButton;

  /**
   * Initializes objects and layout and starts the application.
   *
   * @param stage the stage to be shown
   */
  @Override
  public void start(Stage stage) {

    ChaosGameControllerSingleton.getInstance().addObserver(
        ChaosGameControllerSingleton.getInstance().getChaosGameView());

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
    HBox row2 = new HBox(10);
    row2.getChildren().add(ChaosGameControllerSingleton.getInstance().getChaosGameView().getCanvas());
    row2.setAlignment(Pos.CENTER);
    row2.setPadding(new Insets(100));
    mainLayout.getChildren().add(row2);
    HBox row3 = new HBox(10);
    row3.getChildren().add(bottomLayout);
    row3.setAlignment(Pos.CENTER);
    mainLayout.getChildren().add(row3);
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
    createEditMenu(editMenu);
    menuBar.getMenus().addAll(fileMenu, emptyFractalMenu, predefinedMenu, editMenu);
  }

  public void createEditMenu(Menu editMenu) {
    MenuItem editCurrentFractalItem = new MenuItem("Edit current fractal");
    editCurrentFractalItem.setOnAction(this::editMenuAction);
    editMenu.getItems().add(editCurrentFractalItem);
  }

  public void editMenuAction(ActionEvent actionEvent) {
    if (ChaosGameControllerSingleton.getInstance().getChaosGame().getDescription().getTransforms().get(0).getClass() == JuliaTransform.class) {
      AbstractJuliaTransformView editJuliaTransformView = new EditJuliaTransformView();
      editJuliaTransformView.showStage();
    } else if (ChaosGameControllerSingleton.getInstance().getChaosGame().getDescription().getTransforms().get(0).getClass() == AffineTransform2D.class) {
      AbstractAffineTransformView editAffineTransformView = new EditAffineTransformView();
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
      fileHandler.writeToFile(ChaosGameControllerSingleton.getInstance().getChaosGame().getDescription(),
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
      ChaosGameControllerSingleton.getInstance().setChaosGameDescription(fileHandler.readFromFile(loadFileView.getChosenFilePath()));
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
      AbstractAffineTransformView affineTransformView = new AddAffineTransformView();
      affineTransformView.showStage();
    });
    juliaTransformItem.setOnAction(event -> {
      AbstractJuliaTransformView juliaTransformView = new AddJuliaTransformView();
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
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(ChaosGameDescriptionFactory.createBarnsleyDescription()));

    MenuItem sierpinskiTriangleItem = new MenuItem("Sierpinski Triangle");
    sierpinskiTriangleItem.setOnAction(actionEvent ->
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.createSierpinskiDescription()));

    MenuItem juliaSet1Item = new MenuItem("Julia set 1");
    juliaSet1Item.setOnAction(actionEvent ->
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(ChaosGameDescriptionFactory.getJuliaSetDescription1()));

    MenuItem juliaSet2Item = new MenuItem("Julia set 2");
    juliaSet2Item.setOnAction(actionEvent ->
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(ChaosGameDescriptionFactory.getJuliaSetDescription2()));

    MenuItem juliaSet3Item = new MenuItem("Julia set 3");
    juliaSet3Item.setOnAction(actionEvent ->
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(ChaosGameDescriptionFactory.getJuliaSetDescription3()));

    MenuItem juliaSet4Item = new MenuItem("Julia set 4");
    juliaSet4Item.setOnAction(actionEvent ->
        ChaosGameControllerSingleton.getInstance().setChaosGameDescription(ChaosGameDescriptionFactory.getJuliaSetDescription4()));

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
      ChaosGameControllerSingleton.getInstance().runSteps((int) slider.getValue());
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

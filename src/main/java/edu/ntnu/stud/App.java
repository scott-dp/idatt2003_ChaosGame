package edu.ntnu.stud;

import edu.ntnu.stud.controllers.ChaosGameController;
import edu.ntnu.stud.models.chaosgamehandling.ChaosCanvas;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescription;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameDescriptionFactory;
import edu.ntnu.stud.models.chaosgamehandling.ChaosGameFileHandler;
import edu.ntnu.stud.models.exceptions.EmptyFileException;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.AffineTransform2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import edu.ntnu.stud.models.utils.ChaosGameUtils;
import edu.ntnu.stud.views.fileviews.LoadFileView;
import edu.ntnu.stud.views.fileviews.SaveFileView;
import edu.ntnu.stud.views.transformviews.affinetransformviews.AbstractAffineTransformView;
import edu.ntnu.stud.views.transformviews.affinetransformviews.AddAffineTransformView;
import edu.ntnu.stud.views.transformviews.affinetransformviews.EditAffineTransformView;
import edu.ntnu.stud.views.transformviews.juliatransformviews.AbstractJuliaTransformView;
import edu.ntnu.stud.views.transformviews.juliatransformviews.AddJuliaTransformView;
import edu.ntnu.stud.views.transformviews.juliatransformviews.EditJuliaTransformView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The main view class for the ChaosGame application.
 */
public class App extends Application {
  private static final Logger LOGGER = Logger.getLogger(App.class.getName());
  private final ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
  private Slider slider;
  MenuBar menuBar;
  VBox mainLayout;
  HBox bottomLayout;
  Button runButton;
  TextField stepsTextField;

  /**
   * Initializes objects and layout and starts the application.
   *
   * @param stage the stage to be shown
   */
  @Override
  public void start(Stage stage) {
    ChaosGameController.getInstance().addObserver(
        ChaosGameController.getInstance().getChaosGameView());

    createSlider();
    createMenuBar();
    createRunButton();
    createBottomLayout();
    setMainLayout();
    initializeFractalFromConfig();

    stage.setScene(new Scene(mainLayout, 450, 450));
    stage.setMinHeight(800);
    stage.setMinWidth(1000);
    stage.show();
  }

  /**
   * Saves the last fractal the user viewed to a file when the application is closed.
   */
  @Override
  public void stop() {
    try {
      fileHandler.writeChaosGameToFile(
          ChaosGameController.getInstance().getChaosGame().getDescription(),
          "src/main/resources/config/description.txt");
      fileHandler.writeStringToFile("src/main/resources/config/steps.txt",
          stepsTextField.getText());
    } catch (IOException e) {
      LOGGER.log(Level.INFO, e.getMessage());
    }
  }

  /**
   * Handles the scroll event on the canvas and zooms in or out depending on the direction of the
   * scroll. The zoom is centered around the mouse position.
   *
   * @param scrollEvent the scroll event that occurred
   */
  public void handleScrollEvent(ScrollEvent scrollEvent) {
    double zoomFactor = 0.0001;

    double scrollDeltaY = scrollEvent.getDeltaY();

    //mouse position
    double positionX = scrollEvent.getX();
    double positionY = scrollEvent.getY();

    double zoom = scrollDeltaY * zoomFactor;

    //get old coords
    ChaosGameDescription oldDescription = ChaosGameController.getInstance()
        .getChaosGame().getDescription();
    Vector2D oldMin = oldDescription.getMinCoords();
    Vector2D oldMax = oldDescription.getMaxCoords();

    ChaosCanvas chaosGameCanvas = ChaosGameController.getInstance().getChaosGame().getChaosCanvas();
    //Calculate center of zoom
    double centerX = oldMin.getX0()
        + (positionX / chaosGameCanvas.getWidth()) * (oldMax.getX0() - oldMin.getX0());
    double centerY = oldMin.getX1()
        + (positionY / chaosGameCanvas.getHeight()) * (oldMax.getX1() - oldMin.getX1());

    //New coords according to mouse pos and old coords
    Vector2D newMin = new Vector2D(oldMin.getX0()
        + zoom * centerX, oldMin.getX1() + zoom * centerY);
    Vector2D newMax = new Vector2D(oldMax.getX0()
        - zoom * (1 - centerX), oldMax.getX1() - zoom * (1 - centerY));

    if ((newMax.getX0() - newMin.getX0()) < 0.2) {
      //Max zoom so the application doesn't crash
      return;
    }

    ChaosGameController.getInstance().setChaosGameDescription(
        new ChaosGameDescription(newMin, newMax, oldDescription.getTransforms())
    );
  }

  /**
   * Initializes the fractal from the last time the application was closed.
   */
  public void initializeFractalFromConfig() {
    File descriptionFile = new File("src/main/resources/config/description.txt");
    File stepsFile = new File("src/main/resources/config/steps.txt");

    if (descriptionFile.exists() && stepsFile.exists()) {
      try {
        ChaosGameDescription configDescription = fileHandler
            .readFromFile(descriptionFile.getPath());
        int steps = fileHandler.readIntOnFirstLine(stepsFile.getPath());
        ChaosGameController.getInstance().setChaosGameDescription(configDescription);
        runChaosGameSteps(steps);
        stepsTextField.setText(String.valueOf(steps));
      } catch (IOException | NumberFormatException e) {
        ChaosGameUtils.showErrorAlert("Couldn't load fractal from config");
      }
    }
  }

  /**
   * Sets the main layout of the application.
   */
  public void setMainLayout() {
    mainLayout = new VBox();
    mainLayout.getChildren().add(menuBar);
    HBox row2 = new HBox(10);
    row2.getChildren().add(ChaosGameController.getInstance().getChaosGameView().getCanvas());
    row2.getChildren().add(ChaosGameController.getInstance().getChaosGameView()
        .getColorViewCheckBox());
    ChaosGameController.getInstance().getChaosGameView().getCanvas()
        .setOnScroll(this::handleScrollEvent);
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
    slider.setMax(10000);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(1000);
    slider.valueProperty().addListener(this::valueChangeInSliderEvent);
  }

  /**
   * The method that is called when the value of the slider changes. The method sets the text in the
   * text field to the value of the slider and runs the chaos game for the amount of steps specified
   * by the slider.
   *
   * @param observable the observable object that is being observed
   */
  private void valueChangeInSliderEvent(Observable observable) {
    int sliderAmount = (int) slider.getValue();
    stepsTextField.setText(String.valueOf(sliderAmount));
    runChaosGameSteps(sliderAmount);
  }

  /**
   * Runs the chaos game for the given amount of steps, and surrounds the method call with a try
   * catch block to catch any exceptions that might occur.
   *
   * @param steps the amount of steps to run the chaos game for
   */
  private void runChaosGameSteps(int steps) {
    try {
      ChaosGameController.getInstance().runSteps(steps);
    } catch (NumberFormatException e) {
      ChaosGameUtils.showErrorAlert("Couldn't generate fractal out "
          + "of the given transforms because the point did not converge");
    }
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

  /**
   * Creates the edit menu for the application and adds menu items to it. Adds the action
   * {@link #editMenuAction(ActionEvent)} to the menu item.
   *
   * @param editMenu the menu where the menu items are added
   */
  public void createEditMenu(Menu editMenu) {
    MenuItem editCurrentFractalItem = new MenuItem("Edit current fractal");
    editCurrentFractalItem.setOnAction(this::editMenuAction);
    editMenu.getItems().add(editCurrentFractalItem);
  }

  /**
   * The action that is done when the "Edit current fractal" menu item is clicked. The method
   * checks if the current fractal is an affine or Julia fractal, and opens the corresponding
   * view for editing the fractal.
   *
   * @param actionEvent the event that occurred
   */
  public void editMenuAction(ActionEvent actionEvent) {
    if (ChaosGameController.getInstance().getChaosGame().getDescription().getTransforms()
        .get(0).getClass() == JuliaTransform.class) {
      AbstractJuliaTransformView editJuliaTransformView = new EditJuliaTransformView();
      editJuliaTransformView.showStage();
    } else if (ChaosGameController.getInstance().getChaosGame().getDescription().getTransforms()
        .get(0).getClass() == AffineTransform2D.class) {
      AbstractAffineTransformView editAffineTransformView = new EditAffineTransformView();
      editAffineTransformView.showStage();
    } else {
      ChaosGameUtils.showErrorAlert("No fractal chosen");
    }
  }

  /**
   * Creates the file menu for the application and adds menu items to it. Adds the actions
   * {@link #saveFractalToFileAction(ActionEvent)} and
   * {@link #loadFractalFromFileAction(ActionEvent)} to the menu items.
   *
   * @param fileMenu the menu where the menu items are added
   */
  public void createFileMenu(Menu fileMenu) {
    MenuItem saveFractal = new MenuItem("Save fractal");
    MenuItem loadFractal = new MenuItem("Load fractal from file");

    saveFractal.setOnAction(this::saveFractalToFileAction);
    loadFractal.setOnAction(this::loadFractalFromFileAction);

    fileMenu.getItems().addAll(saveFractal, loadFractal);
  }

  /**
   * The action that is done when the "Save fractal" menu item is clicked. The method opens a
   * {@link SaveFileView} and saves the fractal to a file.
   *
   * @param actionEvent the event that occurred
   */
  public void saveFractalToFileAction(ActionEvent actionEvent) {
    SaveFileView saveFileView = new SaveFileView();
    String fileName = saveFileView.getFileNameFromUser();
    try {
      fileHandler.writeChaosGameToFile(ChaosGameController.getInstance().getChaosGame()
              .getDescription(),
          saveFileView.getChosenDirectory().concat("/" + fileName + ".txt"));
    } catch (IOException e) {
      ChaosGameUtils.showErrorAlert(e.getMessage());
    } catch (NullPointerException e) {
      ChaosGameUtils.showErrorAlert("No directory chosen");
    }
  }

  /**
   * The action that is done when the "Load fractal from file" menu item is clicked.
   * The method opens a {@link LoadFileView} and loads the fractal from a file.
   *
   * @param actionEvent the event that occurred
   */
  public void loadFractalFromFileAction(ActionEvent actionEvent) {
    LoadFileView loadFileView = new LoadFileView();
    try {
      ChaosGameController.getInstance().setChaosGameDescription(
          fileHandler.readFromFile(loadFileView.getChosenFilePath()));
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
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.createBarnsleyDescription()));

    MenuItem sierpinskiTriangleItem = new MenuItem("Sierpinski Triangle");
    sierpinskiTriangleItem.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.createSierpinskiDescription()));

    MenuItem levyCurveItem = new MenuItem("Levy Curve");
    levyCurveItem.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.createLevyCurveDescription()));

    MenuItem juliaSet1Item = new MenuItem("Julia set 1");
    juliaSet1Item.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.getJuliaSetDescription1()));

    MenuItem juliaSet2Item = new MenuItem("Julia set 2");
    juliaSet2Item.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.getJuliaSetDescription2()));

    MenuItem juliaSet3Item = new MenuItem("Julia set 3");
    juliaSet3Item.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.getJuliaSetDescription3()));

    MenuItem juliaSet4Item = new MenuItem("Julia set 4");
    juliaSet4Item.setOnAction(actionEvent ->
        ChaosGameController.getInstance().setChaosGameDescription(
            ChaosGameDescriptionFactory.getJuliaSetDescription4()));

    predefinedMenu.getItems().addAll(barnsleyFernItem, sierpinskiTriangleItem, levyCurveItem,
        juliaSet1Item, juliaSet2Item, juliaSet3Item, juliaSet4Item);
  }

  /**
   * Creates the bottom layout of the application containing slider to choose amount of steps
   * and run button.
   */
  public void createBottomLayout() {
    bottomLayout = new HBox(10);
    Button resetSliderButton = new Button("Reset slider");
    resetSliderButton.setOnAction(this::resetSliderButtonAction);
    bottomLayout.getChildren().add(resetSliderButton);
    bottomLayout.getChildren().add(slider);
    stepsTextField = new TextField();
    bottomLayout.getChildren().add(stepsTextField);
    bottomLayout.getChildren().add(runButton);
  }

  private void resetSliderButtonAction(ActionEvent actionEvent) {
    slider.setMax(10000);
  }

  /**
   * Creates the run button and sets the action to run the chaos game for the amount of steps
   * specified by the slider.
   */
  public void createRunButton() {
    runButton = new Button("Run");
    runButton.setOnAction(this::runButtonAction);
  }

  /**
   * The action that is done when the "Run" button is clicked. The method runs the chaos game for
   * the amount of steps specified in the text field.
   *
   * @param actionEvent the event that occurred
   */
  public void runButtonAction(ActionEvent actionEvent) {
    int steps;
    try {
      steps = Integer.parseInt(stepsTextField.getText());
    } catch (NumberFormatException e) {
      ChaosGameUtils.showErrorAlert("Error parsing steps amount " + e.getMessage());
      return;
    }
    if (steps > slider.getMax()) {
      slider.setMax(steps);
    }
    slider.setValue(steps);
    runChaosGameSteps(steps);
  }

  /**
   * Launches the application.
   *
   * @param args the command line arguments
   */
  public void launchApp(String[] args) {
    launch(args);
  }
}

package edu.ntnu.stud;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.fractalgeneration.ChaosGame;
import edu.ntnu.stud.graphics.ChaosCanvas;
import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Transform2D;
import edu.ntnu.stud.models.Vector2D;
import edu.ntnu.stud.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Main {
  static UserInterface ui = new UserInterface();
  public static void main(String[] args) {
    Vector2D min = new Vector2D(0, 0);
    Vector2D max = new Vector2D(1, 1);
    Transform2D sierpinski1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    Transform2D sierpinski2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));
    Transform2D sierpinski3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
    List<Transform2D> transform2DList = new ArrayList<>();
    transform2DList.add(sierpinski1);
    transform2DList.add(sierpinski2);
    transform2DList.add(sierpinski3);

    ChaosGameDescription description = new ChaosGameDescription(min, max, transform2DList);
    ChaosGame game = new ChaosGame(description, 200, 100);
    game.runSteps(10000);
    game.getCanvas().showCanvas();
    ui.init();
    ui.start();
  }
}

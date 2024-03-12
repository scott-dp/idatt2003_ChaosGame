package edu.ntnu.stud.ui;

import edu.ntnu.stud.config.ChaosGameDescription;
import edu.ntnu.stud.fractalgeneration.ChaosGame;
import edu.ntnu.stud.io.ChaosGameFileHandler;
import edu.ntnu.stud.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInterface {
    private final HashMap<Integer,Runnable> menuItems = new HashMap<>();
    private final Scanner input = new Scanner(System.in);
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    ChaosGame game;
    public void init() {
        menuItems.put(1, this::readDescriptionFromFile);
        menuItems.put(2, this::writeDescriptionToFile);
        menuItems.put(3, this::runSteps);
        menuItems.put(4, () -> System.out.println("Show canvas"));
        menuItems.put(5, () -> System.out.println("Exit"));
    }

    public void showMenu() {
        System.out.println("1. Read description from file");
        System.out.println("2. Write description to file");
        System.out.println("3. Run amount of steps");
        System.out.println("4. Show canvas");
        System.out.println("5. Exit");
    }

    public void start() {
        try {
            ChaosGameDescription description = readDescriptionFromFile();
            game = new ChaosGame(description, 100, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Error initializing game: " + e.getMessage());
            // Handle error or retry
        }

        while (true) {
            showMenu();

            System.out.print("Choose an option: ");

            int choice = numberInput();
            if (choice > 0 && choice <= menuItems.size()) {
                menuItems.get(choice).run();
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public int numberInput() {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(input.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid number.\nTry again: ");
            }
        }
        return number;
    }

    public String textInput() {
        String text;
        while (true) {
            text = input.nextLine();
            if (!text.isEmpty()) {
                return text;
            } else {
                System.out.println("Cannot be empty.\nTry again: ");
            }
        }
    }

    public ChaosGameDescription readDescriptionFromFile() {
        ChaosGameDescription newDescription = null;
        System.out.print("Enter the path to the file: ");
        String path = textInput();
        try {
            newDescription = fileHandler.readFromFile(path);
            System.out.println("File read successfully");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return newDescription;
    }

    public void writeDescriptionToFile() {
        System.out.print("Enter the path to the file: ");
        String path = textInput();

        try {
            fileHandler.writeToFile(createDescription(), path);
            System.out.println("File written successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ChaosGameDescription createDescription() {
        ChaosGameDescription description = null;
        boolean isFinished = false;
        while (!isFinished) {
            System.out.println("What kind of Transformation do you want to create?");
            System.out.println("1. Affine");
            System.out.println("2. Julia");
            System.out.print("Choose an option: ");
            int choice = numberInput();

            System.out.println("Enter lower left coordinates: ");
            Vector2D min = createVector();
            System.out.println("Enter upper right coordinates: ");
            Vector2D max = createVector();

            if (choice == 1) {
                System.out.println("Enter the amount of transformations: ");
                int amountOfTransformations = numberInput();
                List<Transform2D> transform2DList = createAffineList(amountOfTransformations);
                description = new ChaosGameDescription(min, max, transform2DList);
                isFinished = true;
            } else if (choice == 2) {
                List<Transform2D> juliaTransformList = createJuliaList();
                description = new ChaosGameDescription(min, max, juliaTransformList);
                isFinished = true;
            } else {
                System.out.println("Invalid option.");
            }
        }
        return description;
    }

    public Vector2D createVector(){
        System.out.println("x0: ");
        double x0 = doubleInput();
        System.out.println("x1: ");
        double x1 = doubleInput();
        return new Vector2D(x0, x1);
    }

    public Complex createComplex() {
        System.out.println("Enter the real part: ");
        double real = doubleInput();
        System.out.println("Enter the imaginary part: ");
        double imaginary = doubleInput();
        return new Complex(real, imaginary);
    }

    public double doubleInput() {
        double number;
        while (true) {
            try {
                number = Double.parseDouble(input.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid number.\nTry again: ");
            }
        }
        return number;
    }

    public List<Transform2D> createAffineList (int amountOfTransformations) {
        List<Transform2D> transform2DList = new ArrayList<>();
        for (int i = 0; i < amountOfTransformations; i++) {
            System.out.println("Enter the matrix for transformation " + (i + 1) + ": ");
            System.out.println("a00: ");
            double a00 = doubleInput();
            System.out.println("a01: ");
            double a01 = doubleInput();
            System.out.println("a10: ");
            double a10 = doubleInput();
            System.out.println("a11: ");
            double a11 = doubleInput();

            System.out.println("Enter the vector for transformation " + (i + 1) + ": ");
            System.out.println("b0: ");
            double b0 = doubleInput();
            System.out.println("b1: ");
            double b1 = doubleInput();

          transform2DList.add(new AffineTransform2D(new Matrix2x2(a00, a01, a10, a11), new Vector2D(b0, b1)));
        }
        return transform2DList;
    }

    public List<Transform2D> createJuliaList () {
        List<Transform2D> juliaTransformList = new ArrayList<>();
        juliaTransformList.add(new JuliaTransform(createComplex(), 1));
        juliaTransformList.add(new JuliaTransform(createComplex(), -1));
        return juliaTransformList;
    }

    public void runSteps() {
        System.out.println("Enter the amount of steps: ");
        int steps = numberInput();
        System.out.println("Running " + steps + " steps");
        game.runSteps(steps);
    }

    public void showCanvas() {
        System.out.println("Showing canvas");
        game.getCanvas().showCanvas();
    }
}

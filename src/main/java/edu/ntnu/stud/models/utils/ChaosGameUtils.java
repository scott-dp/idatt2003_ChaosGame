package edu.ntnu.stud.models.utils;

import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.scene.control.Alert;

/**
 * Utility class for common operations or methods that don't fit in any other class
 * related to the chaos game.
 */
public class ChaosGameUtils {
  /**
   * Checks if two decimal(double in java) numbers are equal based on a delta value.
   *
   * @param a the one number being checked
   * @param b the other number being checked
   * @return true if a, b are equal and false if they are unequal
   */
  public static boolean areDoublesEqual(double a, double b) {
    final double difference = 1e-6;

    return Math.abs(a - b) < difference;
  }

  /**
   * Takes in a decimal number and rounds it to a set amount of decimals.
   *
   * @param a the decimal number being rounded
   * @param decimals the amount of decimal being rounded to
   * @return the rounded decimal number
   */
  public static double roundDoubleToSetDecimals(double a, int decimals) {
    BigDecimal decimalNum = BigDecimal.valueOf(a);
    decimalNum = decimalNum.setScale(decimals, RoundingMode.HALF_UP);

    return decimalNum.doubleValue();
  }

  /**
   * Util method that validates if an integer is a positive integer.
   *
   * @param num the number to be validated
   * @return the number if it is a positive integer
   */
  public static int validatePositiveInteger(int num) throws IllegalArgumentException {
    if (num < 1) {
      throw new IllegalArgumentException("Integer " + num + " has to be greater than 0");
    } else {
      return num;
    }
  }

  /**
   * Validates that min coordinate is smaller than max coordinate in ChaosCanvas constructor.
   *
   * @param min the minimum coordinate
   * @param max the maximum coordinate
   */
  public static void validateMinAndMaxCoords(Vector2D min, Vector2D max)
      throws IllegalArgumentException {
    if (min.equals(max)) {
      throw new IllegalArgumentException("Min and max coords of the fractal are the same");
    } else if (min.getX0() >= max.getX0() || min.getX1() >= max.getX1()) {
      throw new IllegalArgumentException("Min has to be smaller than max. Min is "
          + min + ", max is " + max);
    }
  }

  /**
   * Validates the sign field taken in the constructor for the construction of a
   * {@link JuliaTransform} object. Throws an IllegalArgumentException if the sign is anything
   * else than 1 or -1.
   *
   * @param sign the field being validated
   * @return the sign param if it has e legal value
   */
  public static int validateSignField(int sign) {
    if (sign == 1 || sign == -1) {
      return sign;
    }
    throw new IllegalArgumentException("Sign field for julia "
        + "transform has to be 1 or -1, but is " + sign);
  }

  /**
   * Validates that point param given to getPixel and putPixel is a point in the canvas.
   *
   * @param point point to be checked if it is between min and max coords
   * @param min minimum coordinate
   * @param max maximum coordinate
   */
  public static void verifyPointBetweenMinAndMax(Vector2D point, Vector2D min, Vector2D max) {
    if (point.getX0() < min.getX0() || point.getX1() < min.getX1()) {
      //Point is smaller than min
      throw new IllegalArgumentException("Point has to be between min and max coordinates");
    } else if (point.getX0() > max.getX0() || point.getX1() > max.getX1()) {
      //Point is greater than max
      throw new IllegalArgumentException("Point has to be between min and max coordinates");
    }
  }

  /**
   * Method that shows an alert to the user if the input is invalid.
   *
   * @param errorMessage the error message to be displayed
   */
  public static void showErrorAlert(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);

    alert.showAndWait();
  }
}

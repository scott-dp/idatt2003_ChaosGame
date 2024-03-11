package edu.ntnu.stud.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
  public static int validatePositiveInteger(int num) {
    if (num < 1) {
      throw new IllegalArgumentException("Integer " + num + " has to be greater than 0");
    } else {
      return num;
    }
  }
}

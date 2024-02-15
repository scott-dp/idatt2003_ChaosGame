package edu.ntnu.stud.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChaosGameUtils {
  public static boolean areDoublesEqual(double a, double b){
    final double difference = 1e-6;

    return Math.abs(a-b) < difference;
  }

  public static double roundDoubleToSetDecimals(double a, int decimals){
    BigDecimal decimalNum = BigDecimal.valueOf(a);
    decimalNum = decimalNum.setScale(decimals, RoundingMode.HALF_UP);

    return decimalNum.doubleValue();
  }
}

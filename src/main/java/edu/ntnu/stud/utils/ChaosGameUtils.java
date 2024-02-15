package edu.ntnu.stud.utils;

public class ChaosGameUtils {
  public static boolean areDoublesEqual(double a, double b){
    final double difference = 1e-6;

    return Math.abs(a-b) < difference;
  }
}

package edu.ntnu.stud.models.interfaces;

/**
 * Interface to be implemented by classes that observe a ChaosGame so that they can be updated
 * whenever a ChaosGame is changed.
 */
public interface ChaosGameObserver {
  void update();
}

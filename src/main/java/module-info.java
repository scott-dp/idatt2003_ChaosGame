module idatt2003_2024_mappe_chaosgame {
  requires javafx.controls;
  requires javafx.graphics;
  requires java.logging;

  exports edu.ntnu.stud;
  exports edu.ntnu.stud.models;
  exports edu.ntnu.stud.models.chaosgamehandling;
  exports edu.ntnu.stud.models.observer;
  exports edu.ntnu.stud.models.exceptions;
  exports edu.ntnu.stud.controllers;
  exports edu.ntnu.stud.views to javafx.graphics;
  exports edu.ntnu.stud.views.transformviews.affinetransformviews to javafx.graphics;
  exports edu.ntnu.stud.views.transformviews to javafx.graphics;
  exports edu.ntnu.stud.views.fileviews to javafx.graphics;
  exports edu.ntnu.stud.models.transform;
  exports edu.ntnu.stud.models.mathematics;
  exports edu.ntnu.stud.views.transformviews.juliatransformviews to javafx.graphics;
}
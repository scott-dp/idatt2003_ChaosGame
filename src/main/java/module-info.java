module com.example.idatt2003_2024_mappe_chaosgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ntnu.stud to javafx.fxml;
    exports edu.ntnu.stud;
  exports edu.ntnu.stud.models;
  opens edu.ntnu.stud.models to javafx.fxml;
  exports edu.ntnu.stud.models.ChaosGameHandling;
  opens edu.ntnu.stud.models.ChaosGameHandling to javafx.fxml;
}
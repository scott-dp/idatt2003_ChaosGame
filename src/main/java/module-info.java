module com.example.idatt2003_2024_mappe_chaosgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ntnu.stud to javafx.fxml;
    exports edu.ntnu.stud;
}
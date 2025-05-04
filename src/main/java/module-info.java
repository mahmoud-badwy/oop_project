module com.example.oop_java_ui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop_java_ui to javafx.fxml;
    exports com.example.oop_java_ui;
}
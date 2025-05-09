package com.example.oop_java_ui;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Date;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        UserManager userManager = new UserManager();

        LoginScreen loginScreen = new LoginScreen(primaryStage, userManager);
        loginScreen.show();
   }

    public static void main(String[] args) {
        launch(args);
    }
}
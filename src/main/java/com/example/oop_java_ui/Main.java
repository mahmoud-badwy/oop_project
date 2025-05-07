package com.example.oop_java_ui;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Date;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Database database = new Database();
        UserManager userManager = new UserManager();

        Organizer org = new Organizer(2,"omar",19,"omar","omar",null,UserType.ORGANIZER);
        
        LoginScreen loginScreen = new LoginScreen(primaryStage, userManager);
        loginScreen.show();

//        OrganizerDashboard ord = new OrganizerDashboard(primaryStage, org);
//        ord.show();
   }

    public static void main(String[] args) {
        launch(args);
    }
}
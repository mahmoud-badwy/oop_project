package com.example.oop_java_ui;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Date;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        UserManager userManager = new UserManager();
        Database db = new Database();
        
        Session session = db.isSessionExists();
        if (session.exists()) {
            User user = session.getUser();
            if (user != null) {
                switch (user.getUserType()) {
                    case ORGANIZER:
                        OrganizerDashboard ord = new OrganizerDashboard(primaryStage, (Organizer) user);
                        ord.show();
                        break;
                    case ATTENDEE:
                        AttendeeDashboard attendeeDashboard = new AttendeeDashboard(primaryStage, (Attendee) user);
                        attendeeDashboard.show();
                        break;
                    case ADMIN:
                        AdminDashboard adminDashboard = new AdminDashboard(primaryStage, (Admin) user);
                        adminDashboard.show();
                        break;
                    default:
                        showLoginScreen(primaryStage, userManager);
                }
            } else {
                showLoginScreen(primaryStage, userManager);
            }
        } else {
            showLoginScreen(primaryStage, userManager);
        }
    }

    private void showLoginScreen(Stage stage, UserManager userManager) {
        LoginScreen loginScreen = new LoginScreen(stage, userManager);
        loginScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
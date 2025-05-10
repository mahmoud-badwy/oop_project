package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginScreen {
    private Stage stage;
    private UserManager userManager;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;

    public LoginScreen(Stage stage, UserManager userManager) {
        this.stage = stage;
        this.userManager = userManager;
    }

    public void show() {
        // Create the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setStyle("-fx-background-color: #f5f5f5;");

        // Title
        Label titleLabel = new Label("Event Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Login form
        GridPane loginForm = new GridPane();
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setHgap(10);
        loginForm.setVgap(10);
        loginForm.setPadding(new Insets(20));

        // Username field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-weight: bold;");
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(250);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(250);

        // Message label for errors
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c;");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        loginButton.setPrefWidth(250);
        loginButton.setOnAction(e -> handleLogin());

        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        registerLink.setOnAction(e -> showRegistrationScreen());

        // Add components to the form
        loginForm.add(usernameLabel, 0, 0);
        loginForm.add(usernameField, 0, 1);
        loginForm.add(passwordLabel, 0, 2);
        loginForm.add(passwordField, 0, 3);
        loginForm.add(loginButton, 0, 4);
        loginForm.add(registerLink, 0, 5);
        loginForm.add(messageLabel, 0, 6);

        // Add components to main container
        mainContainer.getChildren().addAll(titleLabel, loginForm);

        // Create and show the scene
        Scene scene = new Scene(mainContainer, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields");
            return;
        }

        User user = userManager.login(username, password);
        if (user != null) {
            switch (user.getUserType()) {
                case ORGANIZER:
                    Organizer organizer = (Organizer) user;
                    OrganizerDashboard ord = new OrganizerDashboard(stage, organizer);
                    ord.show();
                    break;

                case ATTENDEE:
                    Attendee attendee = (Attendee) user;
                    AttendeeDashboard attendeeDashboard = new AttendeeDashboard(stage, attendee);
                    attendeeDashboard.show();
                    break;

                case ADMIN:
                    Admin admin = (Admin) user;
                    AdminDashboard adminDashboard = new AdminDashboard(stage, admin);
                    adminDashboard.show();
                    break;
            }
            System.out.println("Login successful for user: " + user.getUserType());
        } else {
            messageLabel.setText("Invalid username or password");
        }

    }

    private void showRegistrationScreen() {
        RegistrationScreen registrationScreen = new RegistrationScreen(stage, userManager);
        registrationScreen.show();
    }
} 
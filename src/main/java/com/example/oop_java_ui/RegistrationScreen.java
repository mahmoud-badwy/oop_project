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
import java.util.Date;
import java.time.ZoneId;

public class RegistrationScreen {
    private Stage stage;
    private UserManager userManager;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField emailField;
    private TextField nameField;
    private TextField ageField;
    private DatePicker birthdayPicker;
    private ComboBox<UserType> userTypeComboBox;
    private Label messageLabel;

    public RegistrationScreen(Stage stage, UserManager userManager) {
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
        Label titleLabel = new Label("Create New Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Registration form
        GridPane registrationForm = new GridPane();
        registrationForm.setAlignment(Pos.CENTER);
        registrationForm.setHgap(10);
        registrationForm.setVgap(10);
        registrationForm.setPadding(new Insets(20));

        // Name field
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setPrefWidth(250);

        // Age field
        Label ageLabel = new Label("Age:");
        ageLabel.setStyle("-fx-font-weight: bold;");
        ageField = new TextField();
        ageField.setPromptText("Enter your age");
        ageField.setPrefWidth(250);

        // Birthday field
        Label birthdayLabel = new Label("Birthday:");
        birthdayLabel.setStyle("-fx-font-weight: bold;");
        birthdayPicker = new DatePicker();
        birthdayPicker.setPrefWidth(250);

        // Email field
        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-weight: bold;");
        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefWidth(250);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;");
        passwordField = new PasswordField();
        passwordField.setPromptText("Choose a password");
        passwordField.setPrefWidth(250);

        // Confirm Password field
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setStyle("-fx-font-weight: bold;");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setPrefWidth(250);

        // User Type ComboBox
        Label userTypeLabel = new Label("User Type:");
        userTypeLabel.setStyle("-fx-font-weight: bold;");
        userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll(UserType.values());
        userTypeComboBox.setValue(UserType.ATTENDEE);
        userTypeComboBox.setPrefWidth(250);

        // Message label for errors
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c;");

        // Register button
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        registerButton.setPrefWidth(250);
        registerButton.setOnAction(e -> handleRegistration());

        // Back to login link
        Hyperlink backToLoginLink = new Hyperlink("Already have an account? Login here");
        backToLoginLink.setOnAction(e -> showLoginScreen());

        // Add components to the form
        registrationForm.add(nameLabel, 0, 0);
        registrationForm.add(nameField, 0, 1);
        registrationForm.add(ageLabel, 0, 2);
        registrationForm.add(ageField, 0, 3);
        registrationForm.add(birthdayLabel, 0, 4);
        registrationForm.add(birthdayPicker, 0, 5);
        registrationForm.add(emailLabel, 0, 6);
        registrationForm.add(emailField, 0, 7);
        registrationForm.add(passwordLabel, 0, 8);
        registrationForm.add(passwordField, 0, 9);
        registrationForm.add(confirmPasswordLabel, 0, 10);
        registrationForm.add(confirmPasswordField, 0, 11);
        registrationForm.add(userTypeLabel, 0, 12);
        registrationForm.add(userTypeComboBox, 0, 13);
        registrationForm.add(registerButton, 0, 14);
        registrationForm.add(backToLoginLink, 0, 15);
        registrationForm.add(messageLabel, 0, 16);

        // Add components to main container
        mainContainer.getChildren().addAll(titleLabel, registrationForm);

        // Create and show the scene
        Scene scene = new Scene(mainContainer, 400, 700);
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.show();
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String name = nameField.getText();
        String ageText = ageField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        UserType userType = userTypeComboBox.getValue();
        Date birthday = null;
        if (birthdayPicker.getValue() != null) {
            birthday = Date.from(birthdayPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        // Validate input
        if (username.isEmpty() || name.isEmpty() || ageText.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || birthday == null) {
            messageLabel.setText("Please fill in all fields");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            messageLabel.setText("Age must be a number");
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match");
            return;
        }

        // Attempt to register the user
        User newUser = userManager.registerUser(name, age, username, password, birthday, userType);
        if (newUser != null) {
            messageLabel.setStyle("-fx-text-fill: #2ecc71;");
            messageLabel.setText("Registration successful! Please login.");
            // Automatically switch to login screen after successful registration
            showLoginScreen();
        } else {
            messageLabel.setText("Registration failed. Username might already exist.");
        }
    }

    private void showLoginScreen() {
        LoginScreen loginScreen = new LoginScreen(stage, userManager);
        loginScreen.show();
    }
} 
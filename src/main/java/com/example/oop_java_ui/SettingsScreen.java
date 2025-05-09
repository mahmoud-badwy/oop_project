package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsScreen {
    private User currentUser;
    private UserManager userManager;
    private TextField nameField;
    private TextField ageField;
    private TextField birthdayField;
    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Stage stage;

    public SettingsScreen(Stage stage, User user) {
        this.stage = stage;
        this.currentUser = user;
        this.userManager = new UserManager();
        initializeUI();
    }

    private void initializeUI() {
        stage.setTitle("Settings");
        
        TabPane tabbedPane = new TabPane();
        
        // Profile Settings Tab
        Tab profileTab = new Tab("Profile Settings");
        profileTab.setContent(createProfilePanel());
        profileTab.setClosable(false);
        
        // Password Settings Tab
        Tab passwordTab = new Tab("Change Password");
        passwordTab.setContent(createPasswordPanel());
        passwordTab.setClosable(false);
        
        // Account Settings Tab
        Tab accountTab = new Tab("Account Settings");
        accountTab.setContent(createAccountPanel());
        accountTab.setClosable(false);
        
        tabbedPane.getTabs().addAll(profileTab, passwordTab, accountTab);
        
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #ecf0f1;");
        
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        mainContainer.getChildren().addAll(titleLabel, tabbedPane);
        
        Scene scene = new Scene(mainContainer, 600, 500);
        stage.setScene(scene);
    }

    private VBox createProfilePanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);
        
        nameField = new TextField(currentUser.getName());
        ageField = new TextField(String.valueOf(currentUser.getAge()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        birthdayField = new TextField(sdf.format(currentUser.getBirthday()));
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Age:"), 0, 1);
        grid.add(ageField, 1, 1);
        grid.add(new Label("Birthday (yyyy-MM-dd):"), 0, 2);
        grid.add(birthdayField, 1, 2);
        
        Button updateButton = new Button("Update Profile");
        updateButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setOnAction(e -> updateProfile());
        
        panel.getChildren().addAll(grid, updateButton);
        return panel;
    }

    private VBox createPasswordPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);
        
        currentPasswordField = new PasswordField();
        newPasswordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(new Label("Current Password:"), 0, 0);
        grid.add(currentPasswordField, 1, 0);
        grid.add(new Label("New Password:"), 0, 1);
        grid.add(newPasswordField, 1, 1);
        grid.add(new Label("Confirm New Password:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);
        
        Button changeButton = new Button("Change Password");
        changeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        changeButton.setOnAction(e -> changePassword());
        
        panel.getChildren().addAll(grid, changeButton);
        return panel;
    }

    private VBox createAccountPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);
        
        Label statusLabel = new Label("Account Status: " + (currentUser.isActive() ? "Active" : "Deactivated"));
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        Button toggleButton = new Button(currentUser.isActive() ? "Deactivate Account" : "Reactivate Account");
        toggleButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        toggleButton.setOnAction(e -> toggleAccountStatus(toggleButton, statusLabel));
        
        panel.getChildren().addAll(statusLabel, toggleButton);
        return panel;
    }

    private void updateProfile() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(birthdayField.getText());

            if (userManager.updateUserProfile(currentUser.getId(), name, age, birthday)) {
                showAlert("Success", "Profile updated successfully!", Alert.AlertType.INFORMATION);
                currentUser = userManager.getUserById(currentUser.getId());
            } else {
                showAlert("Error", "Failed to update profile.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid age.", Alert.AlertType.ERROR);
        } catch (ParseException e) {
            showAlert("Error", "Please enter a valid date (yyyy-MM-dd).", Alert.AlertType.ERROR);
        }
    }

    private void changePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "New passwords do not match.", Alert.AlertType.ERROR);
            return;
        }

        if (userManager.changePassword(currentUser.getId(), currentPassword, newPassword)) {
            showAlert("Success", "Password changed successfully!", Alert.AlertType.INFORMATION);
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();
        } else {
            showAlert("Error", "Failed to change password. Please check your current password.", 
                Alert.AlertType.ERROR);
        }
    }

    private void toggleAccountStatus(Button button, Label statusLabel) {
        boolean success;
        if (currentUser.isActive()) {
            success = userManager.deactivateAccount(currentUser.getId());
        } else {
            success = userManager.reactivateAccount(currentUser.getId());
        }

        if (success) {
            currentUser = userManager.getUserById(currentUser.getId());
            statusLabel.setText("Account Status: " + (currentUser.isActive() ? "Active" : "Deactivated"));
            button.setText(currentUser.isActive() ? "Deactivate Account" : "Reactivate Account");
            showAlert("Success", 
                currentUser.isActive() ? "Account reactivated successfully!" : "Account deactivated successfully!",
                Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update account status.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 
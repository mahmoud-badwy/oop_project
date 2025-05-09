package com.example.oop_java_ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class AdminDashboard {
    private Admin admin;
    private VBox mainLayout;
    private TextArea displayArea;
    private Stage stage;
    private VBox createRoomSection;
    private VBox updateRoomSection;
    private VBox deleteRoomSection;
    private VBox searchRoomSection;
    private VBox eventDetailsSection;
    private VBox attendeesSection;
    private VBox operationArea;

    // Color constants
    private static final String PRIMARY_COLOR = "#2c3e50";    // Dark blue
    private static final String SECONDARY_COLOR = "#3498db";  // Light blue
    private static final String ACCENT_COLOR = "#e74c3c";     // Red
    private static final String BACKGROUND_COLOR = "#ecf0f1"; // Light gray
    private static final String TEXT_COLOR = "#2c3e50";       // Dark blue for text

    public AdminDashboard(Stage stage, Admin admin) {
        this.admin = admin;
        this.stage = stage;
    }

    private void createUI() {
        stage.setTitle("Admin Dashboard");

        // Initialize all VBox sections
        createRoomSection = new VBox(10);
        updateRoomSection = new VBox(10);
        deleteRoomSection = new VBox(10);
        searchRoomSection = new VBox(10);
        eventDetailsSection = new VBox(10);
        attendeesSection = new VBox(10);
        operationArea = new VBox(10);

        // Set initial visibility
        createRoomSection.setVisible(false);
        updateRoomSection.setVisible(false);
        deleteRoomSection.setVisible(false);
        searchRoomSection.setVisible(false);
        eventDetailsSection.setVisible(false);
        attendeesSection.setVisible(false);
        operationArea.setVisible(false);

        // Style the operation area
        operationArea.setPadding(new Insets(10));
        operationArea.setMaxWidth(400);
        operationArea.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-border-width: 2;");

        // Main layout
        mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #f0f0f0;");

        // Welcome Message
        Label welcomeLabel = new Label("Welcome, " + admin.getName());
        welcomeLabel.setFont(Font.font(20));
        welcomeLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Add Settings Button
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 15;");
        settingsButton.setOnAction(e -> showSettings());

        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().addAll(welcomeLabel, settingsButton);

        mainLayout.getChildren().add(topBar);

        // Display Area
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefRowCount(12);
        displayArea.setPrefHeight(300);
        displayArea.setWrapText(true);
        displayArea.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-font-size: 14px;");
        VBox.setVgrow(displayArea, Priority.ALWAYS);

        // Create ScrollPane for the display area
        ScrollPane scrollPane = new ScrollPane(displayArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainLayout.getChildren().add(scrollPane);

        // Create a horizontal box to hold room and event sections
        HBox sectionsContainer = new HBox(15);
        sectionsContainer.setPadding(new Insets(15));
        sectionsContainer.setPrefWidth(900);

        // Room Management Section
        VBox roomSection = new VBox(15);
        roomSection.setPadding(new Insets(15));
        roomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-padding: 15;");
        roomSection.setPrefWidth(400);
        roomSection.setMaxWidth(400);

        Label roomTitle = new Label("Room Management");
        roomTitle.setFont(Font.font(16));
        roomTitle.setStyle("-fx-text-fill: #2c3e50;");
        roomSection.getChildren().add(roomTitle);

        // Room Operations Buttons
        VBox roomButtons = new VBox(10);
        Button viewRoomsBtn = new Button("View All Rooms");
        Button createRoomBtn = new Button("Create Room");
        Button updateRoomBtn = new Button("Update Room");
        Button deleteRoomBtn = new Button("Delete Room");
        Button searchByNameBtn = new Button("Search by Name");
        Button searchByIdBtn = new Button("Search by ID");

        String buttonStyle = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 15; -fx-min-width: 150;";
        viewRoomsBtn.setStyle(buttonStyle);
        createRoomBtn.setStyle(buttonStyle);
        updateRoomBtn.setStyle(buttonStyle);
        deleteRoomBtn.setStyle(buttonStyle);
        searchByNameBtn.setStyle(buttonStyle);
        searchByIdBtn.setStyle(buttonStyle);

        roomButtons.getChildren().addAll(viewRoomsBtn, createRoomBtn, updateRoomBtn, deleteRoomBtn, searchByNameBtn, searchByIdBtn);
        roomSection.getChildren().add(roomButtons);

        // Add room section to sections container
        sectionsContainer.getChildren().add(roomSection);

        // Add the sections container and operation area to the main layout
        mainLayout.getChildren().add(sectionsContainer);
        mainLayout.getChildren().add(operationArea);

        // Setup dynamic resizing
        stage.widthProperty().addListener((obs, oldVal, newVal) -> adjustLayout(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> adjustLayout(newVal.doubleValue()));

        // Scene setup
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Dynamically adjust layout based on window size
    private void adjustLayout(double width) {
        // Adjust width of the main layout based on the stage's width
        mainLayout.setMaxWidth(width - 50);  // Adding margin for padding
        operationArea.setMaxWidth(width - 100);  // Adjust operation area width as well
    }

    private void showSettings() {
        package com.example.oop_java_ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class AdminDashboard {
    private Admin admin;
    private VBox mainLayout;
    private TextArea displayArea;
    private Stage stage;
    private VBox createRoomSection;
    private VBox updateRoomSection;
    private VBox deleteRoomSection;
    private VBox searchRoomSection;
    private VBox eventDetailsSection;
    private VBox attendeesSection;
    private VBox operationArea;

    // Color constants
    private static final String PRIMARY_COLOR = "#2c3e50";    // Dark blue
    private static final String SECONDARY_COLOR = "#3498db";  // Light blue
    private static final String ACCENT_COLOR = "#e74c3c";     // Red
    private static final String BACKGROUND_COLOR = "#ecf0f1"; // Light gray
    private static final String TEXT_COLOR = "#2c3e50";       // Dark blue for text

    public AdminDashboard(Stage stage, Admin admin) {
        this.admin = admin;
        this.stage = stage;
    }

    private void createUI() {
        stage.setTitle("Admin Dashboard");

        // Initialize all VBox sections
        createRoomSection = new VBox(10);
        updateRoomSection = new VBox(10);
        deleteRoomSection = new VBox(10);
        searchRoomSection = new VBox(10);
        eventDetailsSection = new VBox(10);
        attendeesSection = new VBox(10);
        operationArea = new VBox(10);

        // Set initial visibility
        createRoomSection.setVisible(false);
        updateRoomSection.setVisible(false);
        deleteRoomSection.setVisible(false);
        searchRoomSection.setVisible(false);
        eventDetailsSection.setVisible(false);
        attendeesSection.setVisible(false);
        operationArea.setVisible(false);

        // Style the operation area
        operationArea.setPadding(new Insets(10));
        operationArea.setMaxWidth(400);
        operationArea.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-border-width: 2;");

        // Main layout
        mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #f0f0f0;");

        // Welcome Message
        Label welcomeLabel = new Label("Welcome, " + admin.getName());
        welcomeLabel.setFont(Font.font(20));
        welcomeLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Add Settings Button
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 15;");
        settingsButton.setOnAction(e -> showSettings());

        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getChildren().addAll(welcomeLabel, settingsButton);

        mainLayout.getChildren().add(topBar);

        // Display Area
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefRowCount(12);
        displayArea.setPrefHeight(300);
        displayArea.setWrapText(true);
        displayArea.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-font-size: 14px;");
        VBox.setVgrow(displayArea, Priority.ALWAYS);

        // Create ScrollPane for the display area
        ScrollPane scrollPane = new ScrollPane(displayArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainLayout.getChildren().add(scrollPane);

        // Create a horizontal box to hold room and event sections
        HBox sectionsContainer = new HBox(15);
        sectionsContainer.setPadding(new Insets(15));
        sectionsContainer.setPrefWidth(900);

        // Room Management Section
        VBox roomSection = new VBox(15);
        roomSection.setPadding(new Insets(15));
        roomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-padding: 15;");
        roomSection.setPrefWidth(400);
        roomSection.setMaxWidth(400);

        Label roomTitle = new Label("Room Management");
        roomTitle.setFont(Font.font(16));
        roomTitle.setStyle("-fx-text-fill: #2c3e50;");
        roomSection.getChildren().add(roomTitle);

        // Room Operations Buttons
        VBox roomButtons = new VBox(10);
        Button viewRoomsBtn = new Button("View All Rooms");
        Button createRoomBtn = new Button("Create Room");
        Button updateRoomBtn = new Button("Update Room");
        Button deleteRoomBtn = new Button("Delete Room");
        Button searchByNameBtn = new Button("Search by Name");
        Button searchByIdBtn = new Button("Search by ID");

        String buttonStyle = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 15; -fx-min-width: 150;";
        viewRoomsBtn.setStyle(buttonStyle);
        createRoomBtn.setStyle(buttonStyle);
        updateRoomBtn.setStyle(buttonStyle);
        deleteRoomBtn.setStyle(buttonStyle);
        searchByNameBtn.setStyle(buttonStyle);
        searchByIdBtn.setStyle(buttonStyle);

        roomButtons.getChildren().addAll(viewRoomsBtn, createRoomBtn, updateRoomBtn, deleteRoomBtn, searchByNameBtn, searchByIdBtn);
        roomSection.getChildren().add(roomButtons);

        // Add room section to sections container
        sectionsContainer.getChildren().add(roomSection);

        // Add the sections container and operation area to the main layout
        mainLayout.getChildren().add(sectionsContainer);
        mainLayout.getChildren().add(operationArea);

        // Setup dynamic resizing
        stage.widthProperty().addListener((obs, oldVal, newVal) -> adjustLayout(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> adjustLayout(newVal.doubleValue()));

        // Scene setup
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Dynamically adjust layout based on window size
    private void adjustLayout(double width) {
        // Adjust width of the main layout based on the stage's width
        mainLayout.setMaxWidth(width - 50);  // Adding margin for padding
        operationArea.setMaxWidth(width - 100);  // Adjust operation area width as well
    }

    private void showSettings() {
         Stage settingsStage = new Stage();
        new SettingsScreen(settingsStage, admin);
        settingsStage.show();
    }

 
}
 

 

 

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
    
    // Color constants
    private static final String PRIMARY_COLOR = "#2c3e50";    // Dark blue
    private static final String SECONDARY_COLOR = "#3498db";  // Light blue
    private static final String ACCENT_COLOR = "#e74c3c";     // Red
    private static final String BACKGROUND_COLOR = "#ecf0f1"; // Light gray
    private static final String TEXT_COLOR = "#2c3e50";       // Dark blue for text
    private VBox operationArea;  // Changed from Object to VBox

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
        operationArea = new VBox(10);  // Initialize operationArea
        
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
        
        viewRoomsBtn.setOnAction(e -> {
            displayArea.clear();
            Database db = new Database();
            List<Room> rooms = db.readRooms();
            if (rooms != null && !rooms.isEmpty()) {
                StringBuilder roomText = new StringBuilder();
                roomText.append("Available Rooms:\n\n");
                for (Room room : rooms) {
                    roomText.append("Room ID: ").append(room.getId()).append("\n");
                    roomText.append("Name: ").append(room.getName()).append("\n");
                    roomText.append("Capacity: ").append(room.getCapacity()).append("\n");
                    roomText.append("----------------------------------------\n");
                }
                displayArea.setText(roomText.toString());
            } else {
                displayArea.setText("No rooms available.");
            }
        });
        
        createRoomBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(createRoomSection);
            operationArea.setVisible(true);
            createRoomSection.setVisible(true);
        });
        
        updateRoomBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(updateRoomSection);
            operationArea.setVisible(true);
            updateRoomSection.setVisible(true);
        });
        
        deleteRoomBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(deleteRoomSection);
            operationArea.setVisible(true);
            deleteRoomSection.setVisible(true);
        });
        
        searchByNameBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(searchRoomSection);
            operationArea.setVisible(true);
            searchRoomSection.setVisible(true);
        });
        
        searchByIdBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(searchRoomSection);
            operationArea.setVisible(true);
            searchRoomSection.setVisible(true);
        });
        
        roomButtons.getChildren().addAll(viewRoomsBtn, createRoomBtn, updateRoomBtn, deleteRoomBtn, searchByNameBtn, searchByIdBtn);
        roomSection.getChildren().add(roomButtons);

        // Create Room Section
        createRoomSection.setPadding(new Insets(10));
        createRoomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db;");
        createRoomSection.setMaxWidth(380);
        createRoomSection.setVisible(true);  // Make sure it's visible
        
        Label createRoomTitle = new Label("Create New Room");
        createRoomTitle.setFont(Font.font(14));
        createRoomTitle.setStyle("-fx-font-weight: bold;");
        
        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Room ID");
        roomIdField.setPrefWidth(300);
        
        TextField roomNameField = new TextField();
        roomNameField.setPromptText("Room Name");
        roomNameField.setPrefWidth(300);
        
        TextField roomCapacityField = new TextField();
        roomCapacityField.setPromptText("Room Capacity");
        roomCapacityField.setPrefWidth(300);
        
        Button createRoomSubmitBtn = new Button("Create");
        createRoomSubmitBtn.setStyle(buttonStyle);
        createRoomSubmitBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(roomIdField.getText());
                String name = roomNameField.getText();
                int capacity = Integer.parseInt(roomCapacityField.getText());
                
                admin.roomManager.createRoom(name, id);
                
                displayArea.setText("Room created successfully!");
                roomIdField.clear();
                roomNameField.clear();
                roomCapacityField.clear();
                operationArea.setVisible(false);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter valid numbers for ID and capacity.");
            }
        });
        
        Button cancelCreateBtn = new Button("Cancel");
        cancelCreateBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        cancelCreateBtn.setOnAction(e -> {
            operationArea.setVisible(false);
            roomIdField.clear();
            roomNameField.clear();
            roomCapacityField.clear();
        });
        
        HBox createButtons = new HBox(10);
        createButtons.getChildren().addAll(createRoomSubmitBtn, cancelCreateBtn);
        
        createRoomSection.getChildren().addAll(createRoomTitle, roomIdField, roomNameField, roomCapacityField, createButtons);

        // Update Room Section
        updateRoomSection.setPadding(new Insets(10));
        updateRoomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db;");
        updateRoomSection.setMaxWidth(380);
        updateRoomSection.setVisible(true);  // Make sure it's visible
        
        Label updateRoomTitle = new Label("Update Room");
        updateRoomTitle.setFont(Font.font(14));
        updateRoomTitle.setStyle("-fx-font-weight: bold;");
        
        TextField updateRoomIdField = new TextField();
        updateRoomIdField.setPromptText("Room ID to Update");
        updateRoomIdField.setPrefWidth(300);
        
        TextField updateRoomNameField = new TextField();
        updateRoomNameField.setPromptText("New Room Name");
        updateRoomNameField.setPrefWidth(300);
        
        TextField updateRoomCapacityField = new TextField();
        updateRoomCapacityField.setPromptText("New Room Capacity");
        updateRoomCapacityField.setPrefWidth(300);
        
        Button updateRoomSubmitBtn = new Button("Update");
        updateRoomSubmitBtn.setStyle(buttonStyle);
        updateRoomSubmitBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateRoomIdField.getText());
                String name = updateRoomNameField.getText();
                int capacity = Integer.parseInt(updateRoomCapacityField.getText());
                
                admin.roomManager.updateRoom(id, name, capacity);
                
                displayArea.setText("Room updated successfully!");
                updateRoomIdField.clear();
                updateRoomNameField.clear();
                updateRoomCapacityField.clear();
                operationArea.setVisible(false);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter valid numbers for ID and capacity.");
            }
        });
        
        Button cancelUpdateBtn = new Button("Cancel");
        cancelUpdateBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        cancelUpdateBtn.setOnAction(e -> {
            operationArea.setVisible(false);
            updateRoomIdField.clear();
            updateRoomNameField.clear();
            updateRoomCapacityField.clear();
        });
        
        HBox updateButtons = new HBox(10);
        updateButtons.getChildren().addAll(updateRoomSubmitBtn, cancelUpdateBtn);
        
        updateRoomSection.getChildren().addAll(updateRoomTitle, updateRoomIdField, updateRoomNameField, updateRoomCapacityField, updateButtons);

        // Delete Room Section
        deleteRoomSection.setPadding(new Insets(10));
        deleteRoomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db;");
        deleteRoomSection.setMaxWidth(380);
        deleteRoomSection.setVisible(true);  // Make sure it's visible
        
        Label deleteRoomTitle = new Label("Delete Room");
        deleteRoomTitle.setFont(Font.font(14));
        deleteRoomTitle.setStyle("-fx-font-weight: bold;");
        
        TextField deleteRoomIdField = new TextField();
        deleteRoomIdField.setPromptText("Room ID to Delete");
        deleteRoomIdField.setPrefWidth(300);
        
        Button deleteRoomSubmitBtn = new Button("Delete");
        deleteRoomSubmitBtn.setStyle(buttonStyle);
        deleteRoomSubmitBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(deleteRoomIdField.getText());
                admin.roomManager.deleteRoom(id);
                
                displayArea.setText("Room deleted successfully!");
                deleteRoomIdField.clear();
                operationArea.setVisible(false);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter a valid room ID.");
            }
        });
        
        Button cancelDeleteBtn = new Button("Cancel");
        cancelDeleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        cancelDeleteBtn.setOnAction(e -> {
            operationArea.setVisible(false);
            deleteRoomIdField.clear();
        });
        
        HBox deleteButtons = new HBox(10);
        deleteButtons.getChildren().addAll(deleteRoomSubmitBtn, cancelDeleteBtn);
        
        deleteRoomSection.getChildren().addAll(deleteRoomTitle, deleteRoomIdField, deleteButtons);

        // Search Room Section
        searchRoomSection.setPadding(new Insets(10));
        searchRoomSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db;");
        searchRoomSection.setMaxWidth(380);
        searchRoomSection.setVisible(true);  // Make sure it's visible
        
        Label searchRoomTitle = new Label("Search Room");
        searchRoomTitle.setFont(Font.font(14));
        searchRoomTitle.setStyle("-fx-font-weight: bold;");
        
        TextField searchRoomField = new TextField();
        searchRoomField.setPromptText("Enter Room ID or Name");
        searchRoomField.setPrefWidth(300);
        
        Button searchRoomSubmitBtn = new Button("Search");
        searchRoomSubmitBtn.setStyle(buttonStyle);
        searchRoomSubmitBtn.setOnAction(e -> {
            String searchTerm = searchRoomField.getText();
            try {
                int id = Integer.parseInt(searchTerm);
                Room room = admin.roomManager.getRoomById(id);
                if (room != null) {
                    displayArea.setText("Room found:\n" +
                        "ID: " + room.getId() + "\n" +
                        "Name: " + room.getName() + "\n" +
                        "Capacity: " + room.getCapacity());
                } else {
                    displayArea.setText("Room not found.");
                }
            } catch (NumberFormatException ex) {
                admin.roomManager.searchRoomsByName(searchTerm);
            }
            searchRoomField.clear();
            operationArea.setVisible(false);
        });
        
        Button cancelSearchBtn = new Button("Cancel");
        cancelSearchBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        cancelSearchBtn.setOnAction(e -> {
            operationArea.setVisible(false);
            searchRoomField.clear();
        });
        
        HBox searchButtons = new HBox(10);
        searchButtons.getChildren().addAll(searchRoomSubmitBtn, cancelSearchBtn);
        
        searchRoomSection.getChildren().addAll(searchRoomTitle, searchRoomField, searchButtons);

        // Event Management Section
        VBox eventSection = new VBox(15);
        eventSection.setPadding(new Insets(15));
        eventSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-padding: 15;");
        eventSection.setPrefWidth(400);
        eventSection.setMaxWidth(400);
        
        Label eventTitle = new Label("Event Management");
        eventTitle.setFont(Font.font(16));
        eventTitle.setStyle("-fx-text-fill: #2c3e50;");
        eventSection.getChildren().add(eventTitle);

        // Event Operations Buttons
        HBox eventButtons = new HBox(20);
        Button viewAllEventsBtn = new Button("View All Events");
        Button viewEventAttendeesBtn = new Button("View Event Attendees");
        
        viewAllEventsBtn.setStyle(buttonStyle);
        viewEventAttendeesBtn.setStyle(buttonStyle);
        
        viewAllEventsBtn.setOnAction(e -> {
            displayArea.clear();
            List<Event> events = admin.getAllEvents();
            if (events != null && !events.isEmpty()) {
                StringBuilder eventText = new StringBuilder();
                eventText.append("Available Events:\n\n");
                for (Event event : events) {
                    eventText.append("Event ID: ").append(event.getEventId()).append("\n");
                    eventText.append("Name: ").append(event.getEventName()).append("\n");
                    eventText.append("Date: ").append(event.getStartTime()).append("\n");
                    eventText.append("Room: ").append(event.getRoom().getName()).append("\n");
                    eventText.append("----------------------------------------\n");
                }
                displayArea.setText(eventText.toString());
            } else {
                displayArea.setText("No events available.");
            }
        });
        
        viewEventAttendeesBtn.setOnAction(e -> {
            operationArea.getChildren().clear();
            operationArea.getChildren().add(attendeesSection);
            operationArea.setVisible(true);
            attendeesSection.setVisible(true);
        });
        
        eventButtons.getChildren().addAll(viewAllEventsBtn, viewEventAttendeesBtn);
        eventSection.getChildren().add(eventButtons);

        // View Event Attendees Section
        attendeesSection.setPadding(new Insets(10));
        attendeesSection.setStyle("-fx-background-color: white; -fx-border-color: #3498db;");
        attendeesSection.setMaxWidth(380);
        attendeesSection.setVisible(true);  // Make sure it's visible
        
        Label attendeesTitle = new Label("View Event Attendees");
        attendeesTitle.setFont(Font.font(14));
        attendeesTitle.setStyle("-fx-font-weight: bold;");
        
        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Enter Event ID");
        eventIdField.setPrefWidth(300);
        
        Button viewAttendeesBtn = new Button("View Attendees");
        viewAttendeesBtn.setStyle(buttonStyle);
        viewAttendeesBtn.setOnAction(e -> {
            try {
                int eventId = Integer.parseInt(eventIdField.getText());
                List<Event> events = admin.getAllEvents();
                Event selectedEvent = null;
                
                // Find the event with the given ID
                for (Event event : events) {
                    if (event.getEventId() == eventId) {
                        selectedEvent = event;
                        break;
                    }
                }
                
                if (selectedEvent != null) {
                    StringBuilder attendeesText = new StringBuilder();
                    attendeesText.append("Attendees for Event: ").append(selectedEvent.getEventName()).append("\n");
                    attendeesText.append("Event ID: ").append(selectedEvent.getEventId()).append("\n");
                    attendeesText.append("----------------------------------------\n");
                    
                    ArrayList<Attendee> attendees = selectedEvent.getAttendees();
                    if (attendees != null && !attendees.isEmpty()) {
                        for (User attendee : attendees) {
                            attendeesText.append("Name: ").append(attendee.getName()).append("\n");
                            attendeesText.append("ID: ").append(attendee.getId()).append("\n");
                            attendeesText.append("----------------------------------------\n");
                        }
                    } else {
                        attendeesText.append("No attendees registered for this event.");
                    }
                    
                    displayArea.setText(attendeesText.toString());
                } else {
                    displayArea.setText("Event not found. Please check the Event ID.");
                }
                
                eventIdField.clear();
                operationArea.setVisible(false);
            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter a valid event ID (numbers only).");
            }
        });
        
        Button cancelAttendeesBtn = new Button("Cancel");
        cancelAttendeesBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        cancelAttendeesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                operationArea.setVisible(false);
                eventIdField.clear();
            }
        });
        
        HBox attendeesButtons = new HBox(10);
        attendeesButtons.getChildren().addAll(viewAttendeesBtn, cancelAttendeesBtn);
        
        attendeesSection.getChildren().addAll(attendeesTitle, eventIdField, attendeesButtons);

        // Add all sections to the container
        sectionsContainer.getChildren().addAll(roomSection, eventSection);
        mainLayout.getChildren().add(sectionsContainer);

        // Add operation area to the main layout after the sections container
        mainLayout.getChildren().add(operationArea);

        // Bottom buttons
        HBox bottomButtons = new HBox();
        bottomButtons.setPadding(new Insets(10));
        bottomButtons.setSpacing(20);
        bottomButtons.setAlignment(Pos.CENTER_RIGHT); // Align buttons to the right
        
        Button refreshBtn = new Button("Refresh");
        Button logoutBtn = new Button("Logout");
        
        refreshBtn.setStyle(buttonStyle);
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15;");
        
        refreshBtn.setOnAction(e -> {
            viewAllRooms();
            viewAllEvents();
        });
        logoutBtn.setOnAction(e -> Platform.exit());
        
        // Add logout button first to push it to the right
        bottomButtons.getChildren().addAll(refreshBtn, logoutBtn);
        mainLayout.getChildren().add(bottomButtons);

        Scene scene = new Scene(mainLayout, 1000, 800);
        stage.setScene(scene);
    }

    private void viewAllRooms() {
        Platform.runLater(() -> {
            displayArea.clear();
            // Get all rooms and display them in the text area
            List<Room> rooms = admin.roomManager.getAllRooms();
            if (rooms != null && !rooms.isEmpty()) {
                StringBuilder roomText = new StringBuilder();
                roomText.append(" Rooms:\n\n");
                for (Room room : rooms) {
                    roomText.append("Room ID: ").append(room.getId()).append("\n");
                    roomText.append("Name: ").append(room.getName()).append("\n");
                    roomText.append("Capacity: ").append(room.getCapacity()).append("\n");
                    roomText.append("----------------------------------------\n");
                }
                displayArea.setText(roomText.toString());
            } else {
                displayArea.setText("No rooms available.");
            }
        });
    }

    private void viewAllEvents() {
        Platform.runLater(() -> {
            displayArea.clear();
            List<Event> events = admin.getAllEvents();
            if (events != null && !events.isEmpty()) {
                StringBuilder eventText = new StringBuilder();
                eventText.append("Available Events:\n\n");
                for (Event event : events) {
                    eventText.append("Event ID: ").append(event.getEventId()).append("\n");
                    eventText.append("Name: ").append(event.getEventName()).append("\n");
                    eventText.append("Date: ").append(event.getStartTime()).append("\n");
                    eventText.append("Room: ").append(event.getRoom().getName()).append("\n");
                    eventText.append("----------------------------------------\n");
                }
                displayArea.setText(eventText.toString());
            } else {
                displayArea.setText("No events available.");
            }
        });
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void show() {
        createUI();
        Platform.runLater(() -> stage.show());
    }

    private void showSettings() {
        Stage settingsStage = new Stage();
        new SettingsScreen(settingsStage, admin);
        settingsStage.show();
    }
} 
 

 

 

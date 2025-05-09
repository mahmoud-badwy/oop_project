package com.example.oop_java_ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AttendeeDashboard {
    private Attendee currentAttendee;
    private TableView<Event> eventTable;
    private Stage stage;
    private Database database;


    public AttendeeDashboard(Stage stage, Attendee attendee) {
        this.currentAttendee = attendee;
        this.database = new Database();
        this.stage = stage;
        createDashboard();
    }

    private void createDashboard() {
        stage.setTitle("Attendee Dashboard");

        // Create main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Create table for events
        eventTable = new TableView<>();
        
        // Create columns
        TableColumn<Event, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventName()));
        
        TableColumn<Event, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getStartTime().toString()));
        
        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getEventDescription()));
        
        TableColumn<Event, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
            cellData.getValue().getTicketPrice()));

        eventTable.getColumns().addAll(nameColumn, startTimeColumn, descriptionColumn, priceColumn);

        // Create buttons
        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel Registration");
        Button refreshButton = new Button("Refresh Events");
        Button settingsButton = new Button("Settings");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(registerButton, cancelButton, refreshButton, settingsButton);

        // Add components to main layout
        mainLayout.getChildren().addAll(eventTable, buttonBox);

        // Handle register button action
        registerButton.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                currentAttendee.registerForEvent(selectedEvent);
                refreshEventList();
            } else {
                showAlert("Please select an event first!");
            }
        });

        // Handle cancel button action
        cancelButton.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                currentAttendee.cancelRegisterForEvent(selectedEvent);
                refreshEventList();
            } else {
                showAlert("Please select an event first!");
            }
        });

        // Handle refresh button action
        refreshButton.setOnAction(e -> refreshEventList());

        // Handle settings button action
        settingsButton.setOnAction(e -> showSettings());

        // Create scene
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
    }

    private void refreshEventList() {
        // Get events from database and update table
        ObservableList<Event> events = FXCollections.observableArrayList(database.readEvents());
        eventTable.setItems(events);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSettings() {
        Stage settingsStage = new Stage();
        new SettingsScreen(settingsStage, currentAttendee);
        settingsStage.show();
    }

    public void show() {
        refreshEventList();
        stage.show();
    }
}
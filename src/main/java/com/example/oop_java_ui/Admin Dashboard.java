package com.example.oop_java_ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminDashboard extends Stage {
    private Admin admin;
    private TextArea displayArea;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        createUI();
    }

    private void createUI() {
        setTitle("Admin Dashboard");

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Display area
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefRowCount(15);
        mainLayout.getChildren().add(displayArea);

        // Buttons panel
        HBox buttonPanel = new HBox(10);
        buttonPanel.setPadding(new Insets(10));

        // Room management buttons
        Button viewRoomsButton = new Button("View All Rooms");
        viewRoomsButton.setOnAction(e -> viewAllRooms());

        Button addRoomButton = new Button("Add Room");
        addRoomButton.setOnAction(e -> showAddRoomDialog());

        Button searchRoomButton = new Button("Search Room");
        searchRoomButton.setOnAction(e -> showSearchRoomDialog());

        // Event management buttons
        Button viewEventsButton = new Button("View All Events");
        viewEventsButton.setOnAction(e -> viewAllEvents());

        Button viewAttendeesButton = new Button("View Event Attendees");
        viewAttendeesButton.setOnAction(e -> showViewAttendeesDialog());

        buttonPanel.getChildren().addAll(
            viewRoomsButton,
            addRoomButton,
            searchRoomButton,
            viewEventsButton,
            viewAttendeesButton
        );

        mainLayout.getChildren().add(buttonPanel);

        // Create scene
        Scene scene = new Scene(mainLayout, 600, 400);
        setScene(scene);
    }

    private void viewAllRooms() {
        displayArea.clear();
        admin.getAllrooms();
    }

    private void showAddRoomDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Room");
        dialog.setHeaderText("Enter Room Details");

        // Create the custom dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        TextField capacityField = new TextField();

        grid.add(new Label("Room Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Capacity:"), 0, 1);
        grid.add(capacityField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return nameField.getText() + "," + capacityField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            String[] parts = result.split(",");
            try {
                String name = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                admin.Createroom(name, capacity);
                viewAllRooms();
            } catch (Exception e) {
                showError("Invalid input. Please enter valid room details.");
            }
        });
    }

    private void showSearchRoomDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Search Room");
        dialog.setHeaderText("Enter Room Name to Search");

        TextField searchField = new TextField();
        dialog.getDialogPane().setContent(searchField);

        ButtonType searchButtonType = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == searchButtonType) {
                return searchField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(roomName -> {
            if (!roomName.isEmpty()) {
                admin.searchRoomsByName(roomName);
            }
        });
    }

    private void viewAllEvents() {
        displayArea.clear();
        List<Event> events = admin.getAllEvents();
        if (events != null && !events.isEmpty()) {
            StringBuilder eventText = new StringBuilder();
            eventText.append("Available Events:\n\n");
            for (Event event : events) {
                eventText.append("Event ID: ").append(event.getId()).append("\n");
                eventText.append("Name: ").append(event.getName()).append("\n");
                eventText.append("Date: ").append(event.getDate()).append("\n");
                eventText.append("Location: ").append(event.getLocation()).append("\n");
                eventText.append("----------------------------------------\n");
            }
            displayArea.setText(eventText.toString());
        } else {
            displayArea.setText("No events available.");
        }
    }

    private void showViewAttendeesDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("View Attendees");
        dialog.setHeaderText("Enter Event ID");

        TextField eventIdField = new TextField();
        dialog.getDialogPane().setContent(eventIdField);

        ButtonType viewButtonType = new ButtonType("View", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(viewButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == viewButtonType) {
                return eventIdField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(eventId -> {
            try {
                int id = Integer.parseInt(eventId);
                List<Event> events = admin.getAllEvents();
                for (Event event : events) {
                    if (event.getId() == id) {
                        admin.showattendes(event);
                        return;
                    }
                }
                showError("Event not found");
            } catch (NumberFormatException e) {
                showError("Please enter a valid event ID");
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

    public void showDashboard() {
        show();
    }
}

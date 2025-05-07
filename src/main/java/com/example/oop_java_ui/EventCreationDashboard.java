package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalTime;

public class EventCreationDashboard {
    private Organizer organizer;

    public EventCreationDashboard(Stage stage, Organizer organizer) {
        this.organizer = organizer;

        Label titleLabel = new Label("Create New Event");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Event Name");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Event Description");

        TextField startTimeField = new TextField();
        startTimeField.setPromptText("Start Time (HH:mm)");

        TextField endTimeField = new TextField();
        endTimeField.setPromptText("End Time (HH:mm)");

        TextField ticketPriceField = new TextField();
        ticketPriceField.setPromptText("Ticket Price");

        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        Button createButton = new Button("Create Event");

        Label feedbackLabel = new Label();

        createButton.setOnAction(e -> {
            try {
                int eventId = Integer.parseInt(eventIdField.getText());
                String name = nameField.getText();
                String desc = descriptionField.getText();
                LocalTime start = LocalTime.parse(startTimeField.getText());
                LocalTime end = LocalTime.parse(endTimeField.getText());
                double price = Double.parseDouble(ticketPriceField.getText());
                int cap = Integer.parseInt(capacityField.getText());
                String catName = categoryField.getText();

                Category category = new Category(5,null,null);

                Event event = new Event(eventId, name, desc, start, end, price, cap, category, organizer);
                organizer.createEvent(event);

                feedbackLabel.setText("Event created successfully!");
            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        VBox form = new VBox(10,
                titleLabel,
                eventIdField, nameField, descriptionField,
                startTimeField, endTimeField,
                ticketPriceField, capacityField,
                categoryField,
                createButton, feedbackLabel
        );
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        Scene scene = new Scene(form, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Event Creation");
        stage.show();
    }
}

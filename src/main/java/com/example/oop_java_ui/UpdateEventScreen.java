package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.List;

public class UpdateEventScreen {
    private Organizer organizer;
    private CategoryManager catmanager = new CategoryManager();

    public UpdateEventScreen(Stage stage, Organizer organizer) {
        this.organizer = organizer;

        Label titleLabel = new Label("Update Event");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label idLabel = new Label("ID of Event to be Updated:");
        idLabel.setStyle("-fx-font-weight: bold;");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");

        Label updateLabel = new Label("Enter New Event data:");
        updateLabel.setStyle("-fx-font-weight: bold;");

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

        Label success = new Label();


        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        Button createButton = new Button("Update Event");

        Label feedbackLabel = new Label();

        createButton.setOnAction(e -> {
            try {
                int eventId = Integer.parseInt(eventIdField.getText());
                String name = nameField.getText();
                String desc = descriptionField.getText();
                LocalTime start = LocalTime.parse(startTimeField.getText());
                LocalTime end = LocalTime.parse(endTimeField.getText());
                double price = Double.parseDouble(ticketPriceField.getText());

                String catName = categoryField.getText();

                List<Category> holder = catmanager.searchCategoriesByName(catName);
                Category category = holder.get(0);

                Event event = organizer.getEventById(eventId);
                Database db = new Database();
                db.updateEvent(event);



                System.out.println("Event Deleted");
                success.setText("Event Deleted");

            } catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        VBox form = new VBox(10,
                titleLabel,
                idLabel,
                eventIdField,
                updateLabel,
                nameField, descriptionField,
                startTimeField, endTimeField,
                ticketPriceField,
                categoryField,
                createButton, feedbackLabel , success
        );
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        Scene scene = new Scene(form, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Update Event");
        stage.show();
    }

}

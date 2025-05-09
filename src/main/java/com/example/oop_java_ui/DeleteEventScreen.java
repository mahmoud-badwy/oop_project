package com.example.oop_java_ui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteEventScreen {
    private Organizer loggedInOrganizer;

    public DeleteEventScreen(Stage stage, Organizer organizer) {
        this.loggedInOrganizer = organizer;

        Label instruction = new Label("Enter Event ID to delete:");
        TextField idInput = new TextField();
        idInput.setPromptText("Event ID");


        Label feedback = new Label();
        feedback.setStyle("-fx-text-fill: red;");
        Label success = new Label();


        Button deleteBtn = new Button("Delete Event");
        deleteBtn.setOnAction(e -> {
                    if (idInput.getText().isEmpty()) {
                        // Show error message or warning
                        System.out.println("Event ID is required.");
                    } else {
                        try {
                            int ID = Integer.parseInt(idInput.getText());

                            loggedInOrganizer.deleteEvent(ID);
                            System.out.println("Event Deleted");
                            success.setText("Event Deleted");

                        } catch (NumberFormatException ex) {
                            System.out.println("Please enter a valid number for the Event ID.");
                            feedback.setText("Please enter a valid number for the Event ID.");
                        }
                    }
                });

        VBox layout = new VBox(10, instruction, idInput, deleteBtn, feedback, success);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 300, 200));
        stage.setTitle("Delete Event");
        stage.show();
    }
}
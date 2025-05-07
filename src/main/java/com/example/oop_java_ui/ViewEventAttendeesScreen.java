package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewEventAttendeesScreen {
    Organizer loggedorganizer;


    public ViewEventAttendeesScreen(Stage stage, Organizer loggedorganizer) {
        this.loggedorganizer = loggedorganizer;

        Label titleLabel = new Label("View Attendees");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label idLabel = new Label("ID of Event:");
        idLabel.setStyle("-fx-font-weight: bold;");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");

        Button view = new Button("View Attendees");

        Label feedbackLabel = new Label();

        view.setOnAction(e -> {

            try {
                Stage stage1 = new Stage();
                stage1.setTitle("View Attendees");
                int eventId = Integer.parseInt(eventIdField.getText());


                ListView<Attendee> AttendeeListView = new ListView<>();
                Event event = loggedorganizer.getEventById(eventId);


                AttendeeListView.getItems().addAll(event.getAttendees());


                AttendeeListView.setCellFactory(param -> new ListCell<Attendee>() {
                    @Override
                    protected void updateItem(Attendee item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {

                            setText(Integer.toString(item.getId()) + " | " + item.getName() + " | " + item.getGender().toString());
                        }
                    }

                });
                VBox vbox1 = new VBox(10, new Label("Your Attendees"),AttendeeListView);
                vbox1.setAlignment(Pos.CENTER);
                vbox1.setPadding(new Insets(20));

                // Set the Scene and Stage, then show the new window
                Scene scene = new Scene(vbox1, 400, 400);
                stage1.setScene(scene);
                stage1.show();



            }
            catch (Exception ex) {
                feedbackLabel.setText("Error: " + ex.getMessage());
                feedbackLabel.setStyle("-fx-text-fill: red;");

            }










        });
        VBox form = new VBox(10,
                titleLabel,
                idLabel,
                eventIdField,
                view,
                feedbackLabel

        );
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        Scene scene = new Scene(form, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Update Event");
        stage.show();


    }

}






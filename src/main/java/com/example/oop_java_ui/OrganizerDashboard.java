package com.example.oop_java_ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class OrganizerDashboard {

    private Stage stage;
    private Organizer organizer;

    public OrganizerDashboard(Stage stage, Organizer organizer) {
        this.stage = stage;
        this.organizer = organizer;
    }

    public void show() {
        VBox mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setStyle("-fx-background-color: #ecf0f1;");



        Label titleLabel = new Label("Welcome, " + organizer.getName());
        titleLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 22));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        Label title2Label = new Label("Organizer Dashboard");
        title2Label.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title2Label.setStyle("-fx-text-fill: #2c3e50;");

        Label roomLabel = new Label("Room Management:");
        roomLabel.setFont(Font.font("Noto Sans", FontWeight.BOLD, 18));
        roomLabel.setStyle("-fx-text-fill: #2c3e50;");
        HBox roomWrapper = new HBox(roomLabel);
//        roomWrapper.setAlignment(Pos.CENTER_LEFT);


        Label eventLabel = new Label("Event Management:");
        eventLabel.setFont(Font.font("Noto Sans", FontWeight.BOLD, 18));
        eventLabel.setStyle("-fx-text-fill: #2c3e50;");
        HBox eventWrapper = new HBox(eventLabel);
        eventWrapper.setAlignment(Pos.CENTER_LEFT);


        Label accountLabel = new Label("Account:");
        accountLabel.setFont(Font.font("Noto Sans", FontWeight.BOLD, 18));
        accountLabel.setStyle("-fx-text-fill: #2c3e50;");
        HBox accWrapper = new HBox(accountLabel);
        accWrapper.setAlignment(Pos.CENTER_LEFT);



        // Buttons
        Button createEventBtn = new Button("Create New Event");
        Button viewEventsBtn = new Button("View My Events");
        Button updateEventBtn = new Button("Update Event");
        Button deleteEventBtn = new Button("Delete Event");
        Button bookRoomBtn = new Button("Book Room for Event");
        Button viewAttendeesBtn = new Button("View Attendees");
        Button logoutBtn = new Button("Logout");

        // Style Buttons
        for (Button btn : new Button[]{createEventBtn, viewEventsBtn, updateEventBtn, deleteEventBtn, bookRoomBtn, viewAttendeesBtn, logoutBtn}) {
            btn.setPrefWidth(300);
            btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        }


        createEventBtn.setOnAction(e -> showCreateEventForm());
        viewEventsBtn.setOnAction(e -> viewevents());
        updateEventBtn.setOnAction(e -> showUpdateEventForm());
        deleteEventBtn.setOnAction(e -> showDeleteEventForm());
        bookRoomBtn.setOnAction(e -> showBookRoomForm());
        viewAttendeesBtn.setOnAction(e -> showAttendeeListForm());
        logoutBtn.setOnAction(e -> logout());

        mainContainer.getChildren().addAll(
                title2Label,
                titleLabel,
                eventWrapper,
                createEventBtn,
                viewEventsBtn,
                viewAttendeesBtn,
                updateEventBtn,
                deleteEventBtn,
                roomWrapper,
                bookRoomBtn,
                accWrapper,
                logoutBtn
        );

        Scene scene = new Scene(mainContainer,  700, 600);
        stage.setScene(scene);
        stage.setTitle("Organizer Dashboard");
        stage.show();
    }



    private void showCreateEventForm() {
        System.out.println("Create Event clicked");
        Stage stage = new Stage();
        new EventCreationDashboard(stage,this.organizer);
    }
    private void viewevents() {
        System.out.println("view my events clicked");
        // Create a new Stage for the "View Events" screen
        Stage stage = new Stage();
        stage.setTitle("View Events");

        // Create a ListView to display the list of events
        ListView<Event> eventListView = new ListView<>();


        eventListView.getItems().addAll(organizer.getMyEvents());


        eventListView.setCellFactory(param -> new ListCell<Event>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {

                    setText(item.getEventName() + " | " + item.getStartTime().toString());
                }
            }
        });


        // Add the ListView and Label to a layout container
        VBox vbox = new VBox(10, new Label("Your Events"), eventListView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Set the Scene and Stage, then show the new window
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();

    }

    private void showUpdateEventForm() {
        System.out.println("Update Event clicked");


    }

    private void showDeleteEventForm() {
        System.out.println("Delete Event clicked");
        Stage stage = new Stage();
        new DeleteEventScreen(stage,this.organizer);
    }

    private void showBookRoomForm() {
        System.out.println("Book Room clicked");
        // Future: Pick event, check availability, call bookroom
    }

    private void showAttendeeListForm() {
        System.out.println("View Attendees clicked");

    }

    private void logout() {
        System.out.println("Logging out...");

    }

}

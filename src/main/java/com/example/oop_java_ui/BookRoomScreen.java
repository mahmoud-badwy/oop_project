package com.example.oop_java_ui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookRoomScreen {
    private RoomManager roomManager;
    private Admin admin;
    private Organizer loggedorganizer;

    public BookRoomScreen(Stage stage, Organizer loggedorganizer){
        this.loggedorganizer=loggedorganizer;

        Label titleLabel = new Label("Book Room");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label eventlabel = new Label("Event ID you'd like to book a room for:");
        eventlabel.setStyle("-fx-font-weight: bold;");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");



        Label feedbackLabel = new Label();

        Button book = new Button("Book Room");

        book.setOnAction(e ->{
            try{
                int eID = Integer.parseInt(eventIdField.getText());
                Event event = loggedorganizer.getEventById(eID);

                loggedorganizer.bookroom(event,roomManager,admin);

                feedbackLabel.setText("Room Booked successfully!");





            }
            catch (Exception ex){
                feedbackLabel.setText(ex.getMessage());
                feedbackLabel.setStyle("-fx-text-fill: red;");




            }










        });

        VBox form = new VBox(10,
                titleLabel,
                eventlabel,
                eventIdField,
                book,
                feedbackLabel
        );
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        Scene scene = new Scene(form, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Book Room");
        stage.show();



    }
}

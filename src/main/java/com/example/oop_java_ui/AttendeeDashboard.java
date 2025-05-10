package com.example.oop_java_ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AttendeeDashboard {
    private Attendee currentAttendee;
    private TableView<Event> eventTable;
    private Stage stage;
    private Database database;
    private Label balanceLabel;  // Add this field


    public AttendeeDashboard(Stage stage, Attendee attendee) {
        this.stage = stage;
        this.database = new Database();
        this.currentAttendee = attendee;
        createDashboard();
    }

    private void createDashboard() {
        stage.setTitle("Attendee Dashboard");

        // Create main layout with BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(15));

        // Create header
        VBox headerBox = new VBox(10);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        headerBox.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + currentAttendee.getName());
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Use the class-level balanceLabel instead of creating a new local variable
        this.balanceLabel = new Label("Wallet Balance: "+
                currentAttendee.getWallet().getBalance());
        balanceLabel.setFont(Font.font("System", 16));

        headerBox.getChildren().addAll(welcomeLabel, balanceLabel);
        mainLayout.setTop(headerBox);

        // Create table for events
        eventTable = new TableView<>();
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create columns
        TableColumn<Event, String> nameColumn = new TableColumn<>("Event Name");
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEventName()));
        nameColumn.setPrefWidth(200);

        TableColumn<Event, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStartTime().toString()));
        startTimeColumn.setPrefWidth(150);

        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEventDescription()));
        descriptionColumn.setPrefWidth(300);

        TableColumn<Event, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTicketPrice()));
        priceColumn.setPrefWidth(100);

        eventTable.getColumns().addAll(nameColumn, startTimeColumn, descriptionColumn, priceColumn);
        mainLayout.setCenter(eventTable);

        // Create buttons with styling
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Button registerButton = new Button("Register for Event");
        registerButton.setFont(Font.font("System", 14));
        registerButton.setPadding(new Insets(8, 15, 8, 15));

        Button cancelButton = new Button("Cancel Registration");
        cancelButton.setFont(Font.font("System", 14));
        cancelButton.setPadding(new Insets(8, 15, 8, 15));

        Button refreshButton = new Button("Refresh Events");
        refreshButton.setFont(Font.font("System", 14));
        refreshButton.setPadding(new Insets(8, 15, 8, 15));

        Button settingsButton = new Button("Settings");
        settingsButton.setFont(Font.font("System", 14));
        settingsButton.setPadding(new Insets(8, 15, 8, 15));

        Button editBalanceButton = new Button("Edit Balance");
        editBalanceButton.setFont(Font.font("System", 14));
        editBalanceButton.setPadding(new Insets(8, 15, 8, 15));

        buttonBox.getChildren().addAll(registerButton, cancelButton, refreshButton, editBalanceButton, settingsButton);
        mainLayout.setBottom(buttonBox);

        // Add edit balance button handler
        editBalanceButton.setOnAction(e -> {
            // Create a new dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Balance");
            dialog.setHeaderText("Current Balance: $" +  currentAttendee.getWallet().getBalance());

            // Add buttons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Create layout for dialog
            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(10));

            TextField balanceField = new TextField();
            balanceField.setPromptText("Enter new balance");
            
            Label errorLabel = new Label("");
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            
            dialogContent.getChildren().addAll(
                new Label("New Balance:"),
                balanceField,
                errorLabel
            );

            dialog.getDialogPane().setContent(dialogContent);

            // Validate input when OK is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    try {
                        double newBalance = Double.parseDouble(balanceField.getText());
                        if (newBalance < 0) {
                            errorLabel.setText("Balance cannot be negative");
                            return null;
                        }
                        currentAttendee.getWallet().setBalance(newBalance);
                        System.out.println(currentAttendee.getWallet().getBalance());
                        balanceLabel.setText("Wallet Balance: $"+ currentAttendee.getWallet().getBalance());
                        
//                         Update user in database
                        database.updateUser(currentAttendee);
//
                        showAlert("Success", "Balance updated successfully!", Alert.AlertType.INFORMATION);
                    } catch (NumberFormatException ex) {
                        errorLabel.setText("Please enter a valid number");
                        return null;
                    }
                }
                return dialogButton;
            });

            dialog.showAndWait();
        });

        // Handle register button action
        registerButton.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                try {
                    // Check if user has enough balance
                    if (currentAttendee.getWallet().getBalance() < selectedEvent.getTicketPrice()) {
                        showAlert("Insufficient Balance", 
                            "Your wallet balance ($" +  currentAttendee.getWallet().getBalance() +
                            ") is less than the ticket price ($" +  selectedEvent.getTicketPrice() + ")",
                            Alert.AlertType.ERROR);
                        return;
                    }
                    
                    // Check if event is already registered
                    if (currentAttendee.isRegisteredForEvent(selectedEvent)) {
                        showAlert("Already Registered", 
                            "You are already registered for this event: " + selectedEvent.getEventName(), 
                            Alert.AlertType.WARNING);
                        return;
                    }
                    
                    // Check if event capacity is full
                    if (selectedEvent.getNoofattendees() >= selectedEvent.getCapacity()) {
                        showAlert("Event Full", 
                            "Sorry, this event has reached its maximum capacity.", 
                            Alert.AlertType.WARNING);
                        return;
                    }
                    currentAttendee.registerForEvent(selectedEvent);
                    // Update balance label in UI
                    balanceLabel.setText("Wallet Balance: $" +currentAttendee.getWallet().getBalance());
                    refreshEventList();
                    showAlert("Registration Successful",
                            "You have successfully registered for: " + selectedEvent.getEventName() +
                            "\nTicket Price: $" +  selectedEvent.getTicketPrice() +
                            "\nRemaining Balance: $" + currentAttendee.getWallet().getBalance(),
                            Alert.AlertType.INFORMATION);
                } catch (Exception ex) {
                    showAlert("Registration Failed",
                            "Could not register for the event: " + ex.getMessage(),
                            Alert.AlertType.ERROR);
                }
            } else {
                showAlert("No Event Selected",
                        "Please select an event from the table to register.",
                        Alert.AlertType.WARNING);
            }
        });

        // Handle cancel button action
        cancelButton.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                try {
                    // Check if user is actually registered for this event
                    System.out.println(selectedEvent.getAttendees().toString());
                    System.out.println(currentAttendee.getId());
                    for(Attendee attendee : selectedEvent.getAttendees()){
                        if (attendee.getId() == currentAttendee.getId()) {
                            currentAttendee.cancelRegisterForEvent(selectedEvent);

                            // Update balance label in UI
                            balanceLabel.setText("Wallet Balance: $" + currentAttendee.getWallet().getBalance());
                            refreshEventList();
                            showAlert("Cancellation Successful",
                                    "Your registration for: " + selectedEvent.getEventName() + " has been cancelled.\n" +
                                            "Refunded Amount: $" + (selectedEvent.getTicketPrice()*0.8) +
                                            "\nUpdated Balance: $" + (currentAttendee.getWallet().getBalance()),
                                    Alert.AlertType.INFORMATION);

                            return;


                        }

                    }
                    showAlert("Not Registered",
                            "You are not registered for this event: " + selectedEvent.getEventName(),
                            Alert.AlertType.WARNING);

                } catch (Exception ex) {
                    showAlert("Cancellation Failed",
                            "Could not cancel the registration: " + ex.getMessage(),
                            Alert.AlertType.ERROR);
                }
            } else {
                showAlert("No Event Selected",
                        "Please select an event from the table to cancel registration.",
                        Alert.AlertType.WARNING);
            }
        });

        refreshButton.setOnAction(e -> refreshEventList());
        settingsButton.setOnAction(e -> showSettings());

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);

        // Set a reasonable max size
        stage.setMaxHeight(800);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshEventList() {
        // Update the current attendee object with fresh data from database
        for (User user : database.readUsers()) {
            if (user instanceof Attendee && user.getId() == currentAttendee.getId()) {
                currentAttendee = (Attendee) user;
                balanceLabel.setText("Wallet Balance: $"+ currentAttendee.getWallet().getBalance());
                break;
            }
        }
        
        // Refresh events list
        ObservableList<Event> events = FXCollections.observableArrayList(database.readEvents());
        eventTable.setItems(events);
    }

    public void show() {
        refreshEventList();
        stage.show();
    }

    private void showSettings() {
        Stage settingsStage = new Stage();
        new SettingsScreen(settingsStage, currentAttendee);
        settingsStage.show();
    }
}
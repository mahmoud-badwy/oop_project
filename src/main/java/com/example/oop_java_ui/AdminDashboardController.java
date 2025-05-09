package com.example.oop_java_ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class AdminDashboardController {
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, Integer> roomIdColumn;
    @FXML private TableColumn<Room, String> roomNameColumn;
    @FXML private TableColumn<Room, Integer> roomCapacityColumn;
    
    @FXML private TableView<Event> eventTable;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> eventDateColumn;
    
     private TextField newRoomName;
    @FXML private TextField newRoomCapacity;
    @FXML private TextField updateRoomId;
    @FXML private TextField updateRoomName;
    @FXML private TextField updateRoomCapacity;
    @FXML private TextField deleteRoomId;
    @FXML private TextField searchRoomName;
    @FXML private TextArea eventDetailsArea;
    
    private Admin admin;
    private RoomManager roomManager;
    private Database database;
    
    public void initialize() {
        // Initialize room table columns
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        
        // Initialize event table columns
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        // Initialize managers
        roomManager = new RoomManager();
    }
    
    @FXML
    private void handleShowAllRooms() {
        List<Room> rooms = roomManager.getAllRooms();
        ObservableList<Room> roomData = FXCollections.observableArrayList(rooms);
        roomTable.setItems(roomData);
    }
    
    @FXML
    private void handleCreateRoom() {
        try {
            String name = newRoomName.getText();
            int capacity = Integer.parseInt(newRoomCapacity.getText());
            admin.Createroom(name, capacity);
            handleShowAllRooms();
            clearRoomInputs();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid room capacity");
        }
    }
    
    @FXML
    private void handleUpdateRoom() {
        try {
            int id = Integer.parseInt(updateRoomId.getText());
            String name = updateRoomName.getText();
            int capacity = Integer.parseInt(updateRoomCapacity.getText());
            admin.updateRoom(id, name, capacity);
            handleShowAllRooms();
            clearRoomInputs();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid room ID and capacity");
        }
    }
    
    @FXML
    private void handleDeleteRoom() {
        try {
            int id = Integer.parseInt(deleteRoomId.getText());
            admin.deleteRoom(id);
            handleShowAllRooms();
            clearRoomInputs();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid room ID");
        }
    }
    
    @FXML
    private void handleSearchRoom() {
        String name = searchRoomName.getText();
        admin.searchRoomsByName(name);
        clearRoomInputs();
    }
    
    @FXML
    private void handleShowAllEvents() {
        List<Event> events = admin.getAllEvents();
        ObservableList<Event> eventData = FXCollections.observableArrayList(events);
        eventTable.setItems(eventData);
    }
    
    @FXML
    private void handleShowEventDetails() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            admin.showEvent(selectedEvent);
            admin.showattendes(selectedEvent);
        } else {
            showAlert("Warning", "Please select an event first");
        }
    }
    
    @FXML
    private void handleRefresh() {
        handleShowAllRooms();
        handleShowAllEvents();
    }
    
    @FXML
    private void handleLogout() {
        // Add logout logic here
        System.exit(0);
    }
    
    private void clearRoomInputs() {
        newRoomName.clear();
        newRoomCapacity.clear();
        updateRoomId.clear();
        updateRoomName.clear();
        updateRoomCapacity.clear();
        deleteRoomId.clear();
        searchRoomName.clear();
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 
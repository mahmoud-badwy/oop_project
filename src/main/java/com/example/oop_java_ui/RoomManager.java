package com.example.oop_java_ui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Room> rooms;
    private int nextId;
    Database db = new Database();
    public RoomManager() {
        this.rooms = new ArrayList<>();

        this.rooms = db.readRooms();
        this.nextId = rooms.size() + 1;
    }

    


    public boolean createRoom(String name, int capacity) {
        try{
            rooms = db.readRooms();
            Room room = new Room(nextId++, name, capacity);
            rooms.add(room);
            db.saveRooms(rooms);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room getRoomById(int id) {
        rooms = db.readRooms();

        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        rooms.clear();
        return null;
    }

    public boolean updateRoom(int id, String name, int capacity) {
        rooms=  db.readRooms();

        Room room = getRoomById(id);
        if (room != null) {
            room.setName(name);
            room.setCapacity(capacity);
            db.updateRoom(room);
            return true;
        }
        rooms.clear();
        return false;
    }

    public boolean deleteRoom(int id) {
        rooms=  db.readRooms();
        Room room = getRoomById(id);
        if (room != null) {
            rooms.remove(room);
            db.deleteRoom(id);
            return true;
        }
        System.out.println("room not found");
        return false;
    }
    public void occupyroom(Room room){
        room.setAvailable(false);

    }

    public List<Room> getAvailableRooms(int capacity) {
        Database db = new Database();
        List<Room> availableRooms = db.readRooms();  // Assuming this method fetches the list of rooms

        for (Room room : availableRooms) {
            // Check if room has sufficient capacity
            if (room.getCapacity() >= capacity) {
                // If the room meets the capacity requirement, add it to the available list
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Room> searchRoomsByName(String name) {
        rooms = db.readRooms();
        List<Room> results = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(room);
            }
        }
        rooms.clear();
        return results;
    }
} 

   
      

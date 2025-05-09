package com.example.oop_java_ui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Room> rooms;
    private int nextId;

    public RoomManager() {
        this.rooms = new ArrayList<>();
        this.nextId = 1;
    }

    

  Database db = new Database();
    public Room createRoom(String name, int capacity) {
        Room room = new Room(nextId++, name, capacity);
        rooms.add(room);
         
        db.saveRooms(rooms);
        return room;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room getRoomById(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    public boolean updateRoom(int id, String name, int capacity) {
        Room room = getRoomById(id);
        if (room != null) {
            room.setName(name);
            room.setCapacity(capacity);
            db.updateRoom(room);
            return true;
        }
        return false;
    }

    public boolean deleteRoom(int id) {
        Room room = getRoomById(id);
        if (room != null) {
            rooms.remove(room);
            db.deleteRoom(id); 
            return true;
        }
        return false;
    }
    public void occupyroom(Room room){
        room.setAvailable(false);

    }

    public List<Room> getAvailableRooms(LocalTime startTime, LocalTime endTime, int capacity) {
        Database db = new Database();
        List<Room> availableRooms = db.readRooms();
        Room.TimeSlot targetSlot = new Room.TimeSlot(startTime, endTime);

        for (Room room : rooms) {
            // Check if room has sufficient capacity
            if (room.getCapacity() >= capacity) {
                // Check if the room is available during the requested time slot
                if (room.getAvailableSlots() != null && 
                    room.getAvailableSlots().overlapsWith(targetSlot)) {
                    availableRooms.add(room);
                }
            }
        }

        return availableRooms;
    }

    public List<Room> searchRoomsByName(String name) {
        List<Room> results = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(room);
            }
        }
        return results;
    }
} 

   
      

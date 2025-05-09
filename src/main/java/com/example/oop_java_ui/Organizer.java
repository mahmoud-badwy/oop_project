package com.example.oop_java_ui;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organizer extends User {

    private List<Event> events;

    public Organizer(int id, String name, int age, String userName, String password, Date birthday, UserType userType) {
        super(id, name, age, userName, password, birthday, userType);
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events); // return copy to protect internal list
    }

    public void createEvent(Event event) {
        RoomManager roomManager = new RoomManager();
        List<Room> availableRooms = roomManager.getAvailableRooms(event.getStartTime(), event.getEndTime(), event.getCapacity());
        
        if (!availableRooms.isEmpty()) {
            // Assign the first available room
            Room selectedRoom = availableRooms.get(0);
            event.setRoom(selectedRoom);
            roomManager.occupyroom(selectedRoom);
            
            // Add event to organizer's list
            events.add(event);
            
            // Save event to database
            Database database = new Database();
            List<Event> allEvents = database.readEvents();
            allEvents.add(event);
            database.saveEvents(allEvents);
            
            System.out.println("Event created successfully with room: " + selectedRoom.getName());
        } else {
            System.out.println("No available rooms for the specified time and capacity.");
        }
    }

    public Event getEventById(int eventId) {
        for (Event e : events) {
            if (e.getEventId() == eventId) {
                return e;
            }
        }
        return null;
    }

    public List<Event> getMyEvents() {
        return getEvents();
    }

    public boolean updateEvent(int eventId, Event updatedEvent) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == eventId) {
                events.set(i, updatedEvent);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEvent(int eventId) {
        return events.removeIf(e -> e.getEventId() == eventId);
    }

    public void showMyEvents() {
        for (Event e : events) {
            System.out.println(e);
        }
    }

    public boolean bookRoom(Event event, RoomManager roomManager, Admin admin) {
        List<Room> availableRooms = roomManager.getAvailableRooms(event.getStartTime(), event.getEndTime(), event.getCapacity());
        long hours = Duration.between(event.getStartTime(), event.getEndTime()).toHours();
        long price = hours * 10;
        
        if (!availableRooms.isEmpty()) {
            Room selectedRoom = availableRooms.get(0);
            roomManager.occupyroom(selectedRoom);
            super.getWallet().transfer(price, admin.getWallet());
            event.setRoom(selectedRoom);
            return true;
        }
        return false;
    }

    public void showAvailableRooms(LocalTime startTime, LocalTime endTime, int capacity) {
        RoomManager roomManager = new RoomManager();
        System.out.println(roomManager.getAvailableRooms(startTime, endTime, capacity));
    }

    public void readAttendees(int eventId) {
        Event event = getEventById(eventId);
        if (event != null) {
            System.out.println("Attendees for Event: " + event.getEventName());
            for (Attendee a : event.getAttendees()) {
                System.out.println(a);
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", events=" + events.size() +
                '}';
    }
}

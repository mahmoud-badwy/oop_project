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
    events.add(event);
    Database db = new Database();
    db.saveEvents(events);




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



    // UPDATE
    public boolean updateEvent(int eventId, Event updatedEvent) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == eventId) {
                events.set(i, updatedEvent);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean deleteEvent(int eventId) {
        return events.removeIf(e -> e.getEventId() == eventId);
    }

    // Show all events
    public void showMyEvents() {
        for (Event e : events) {
            System.out.println(e);
        }
    }

    public boolean bookroom(Event event, RoomManager roommanager, Admin admin) {
        List<Room> roomcheck = roommanager.getAvailableRooms(event.getStartTime(), event.getEndTime(), event.getCapacity());
        long hours = Duration.between(event.getStartTime(), event.getEndTime()).toHours();
        long price = hours * 10;
        if (!roomcheck.isEmpty()) {
            roommanager.occupyroom(roomcheck.get(0));
            Organizer.super.getWallet().transfer(price, admin.getWallet());
            event.setRoom(roomcheck.get(0));
            return true;

        } else return false;
    }

    RoomManager m = new RoomManager ();

    void showAvailableROOMS(RoomManager m, LocalTime startTime, LocalTime endTime, int capacity)
    {
        System.out.println( m.getAvailableRooms( startTime,  endTime,  capacity));
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

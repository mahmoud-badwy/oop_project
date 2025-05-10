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
    db.saveEvent(events.get(0));
    events.clear();




    }

    
    

   public Event getEventById(int eventId) {
        Database db = new Database();
    for (Event e : db.readEvents()) {
        if (e.getEventId() == eventId) {
            return e; 
        }
    }
    return null; 
}
    public List<Event> getMyEvents() {
        Database db = new Database();

        List<Event> events = db.readEvents();
        List<Event> results = new ArrayList<>();

        for (Event e : events) {
            if (e.getOrganizer().getId()==(this.getId())) {
                results.add(e);
            }
        }



        return results;
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
    public void deleteEvent(int eventId) {
        Database db = new Database();
        db.deleteEvent(eventId);
    }

    // Show all events
    public void showMyEvents() {
        for (Event e : events) {
            System.out.println(e);
        }
    }

    public boolean bookroom(Event event, RoomManager roommanager, User admin) {
        Database db = new Database();
        System.out.println("check rooms");

        List<Room> roomcheck = db.readRooms();
        for (Room r : roomcheck) {
            if (event.getRoom() == null ) {
                event.setRoom(r);
                // Save changes
                db.updateEvent(event);
                return  true;
            }
            else if (r.getCapacity() >= event.getRoom().getCapacity()) {
                event.setRoom(r);
                // Save changes
                db.updateEvent(event);
                return  true;
            }
        }
        System.out.println("No rooms available for the given time and capacity.");

     return false;
    }

    RoomManager m = new RoomManager ();

//    void showAvailableROOMS(RoomManager m, LocalTime startTime, LocalTime endTime, int capacity)
//    {
//        System.out.println( m.getAvailableRooms( startTime,  endTime,  capacity));
//    }


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

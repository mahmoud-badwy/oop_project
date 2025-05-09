package com.example.oop_java_ui;

import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private LocalTime startTime;
    private LocalTime endTime;
    private double ticketPrice;
    private int capacity;
    private Category category;
    private Room room;
    private Organizer organizer;
    private ArrayList<Attendee> attendees;
    private int noofattendees = 0;

    public int getNoofattendees() {
        return noofattendees;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Event(int eventId, String eventName, String eventDescription, LocalTime startTime, LocalTime endTime, double ticketPrice, int capacity, Category category, Organizer organizer) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.category = category;
        this.room = null;
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
        this.noofattendees = attendees.size();
    }

    //room.bookRoom(this);
    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public Category getCategory() {
        return category;
    }

    public Room getRoom() {
        return room;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setEventName(String eventName) {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        this.eventName = eventName;
    }

    public boolean addAttendee(Attendee attendee) {
        if (attendees.size() == this.capacity) {
            System.out.println("Event is full");
            return false;
        }

        if (attendees.contains(attendee)) {
            System.out.println("Attendee is already in the event");
            return false;
        }
        if (attendee.getWallet().getBalance() < ticketPrice) {
            System.out.println("Balance is not enough");
            return false;
        }
        attendee.getWallet().transfer(ticketPrice,organizer.getWallet());
        UserManager userManager = new UserManager();
        User user =  userManager.getUserById(this.getOrganizer().getId());
        Organizer org = (Organizer) user;
        attendees.add(attendee);
        System.out.println(attendee.getWallet().getBalance());
        Database db = new Database();
        db.updateUser(attendee);
        org.setWallet(organizer.getWallet());
        db.updateUser(org);
        db.updateEvent(this);
        return true;
    }

    public boolean addAttendeewithoutRigester(Attendee attendee) {
        attendees.add(attendee);
        return true;
    }

    public boolean cancelRegistration(Attendee attendee) {

        for(Attendee att: attendees) {
            if(att.getId() == attendee.getId()) {
                attendee.getWallet().deposit(0.8 * ticketPrice);
                attendees.remove(att);
                UserManager userManager = new UserManager();
                User user = userManager.getUserById(this.getOrganizer().getId());
                Organizer org = (Organizer) user;
                System.out.println(org.getWallet().getBalance());
                org.getWallet().withdraw(0.8 * ticketPrice);
                System.out.println(org.getWallet().getBalance());

                attendees.remove(attendee);
                Database db = new Database();
                db.updateUser(attendee);
                db.updateUser(org);
                db.updateEvent(this);

                return true;
            }
        }
        return false;
    }

    public void updateEvent(String newName, String newDescription, LocalTime newStartTime, LocalTime newEndTime, double newTicketPrice, Category newCategory) {
       if (room.isAvailable(this, startTime, endTime))
        {
            this.eventName = newName;
            this.eventDescription = newDescription;
            this.startTime = newStartTime;
            this.endTime = newEndTime;
            this.ticketPrice = newTicketPrice;
            this.category = newCategory;
        }
       else{
            throw new IllegalArgumentException("Room is not available at this time");
        }

    }

    public void updateEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void updateStartTime(LocalTime newStartTime) {
        if(!room.isAvailable(this,startTime,endTime)){
            throw new IllegalArgumentException("Room is not available at this time");
        }
        else {
            this.startTime = newStartTime;
        }
    }

    public void updateEndTime(LocalTime newEndTime) {
        if(!room.isAvailable(this,startTime,endTime)){
            throw new IllegalArgumentException("Room is not available at this time");
        }
        else {
            this.endTime = newEndTime;
        }
    }

    public void updateTicketPrice(double newTicketPrice) {
        this.ticketPrice = newTicketPrice;
    }

    public void updateCapacity(int newCapacity) {
        if(room.getCapacity() < newCapacity) {
            throw new IllegalArgumentException("Room is full");
        }
        else{
        this.capacity = newCapacity;
        }
    }

    public void updateCategory(Category newCategory) {
        this.category = newCategory;
    }

    public void updateRoom(Room newRoom) {
        if(!newRoom.isAvailable(this,startTime,endTime)) {
            throw new IllegalArgumentException("Room is not available");
        }
        else {
            this.room = newRoom;
        }
    }

    public void updateOrganizer(Organizer newOrganizer) {
        this.organizer = newOrganizer;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", ticketPrice=" + ticketPrice +
                ", capacity=" + capacity +
                ", category=" + category +
                ", room=" + room +
                ", organizer=" + organizer +
                ", attendees=" + attendees +
                '}';
    }
}

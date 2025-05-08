package com.example.oop_java_ui;

import java.util.Date;
import java.util.ArrayList;

public class Attendee extends User {
    private Gender gender;
    private String address;
    private ArrayList<Event> events;
    public Attendee(){
        updateUserType();
    }
    public Attendee(int id, String name, int age, String userName, String password, Date birthday,Gender gender, String address,double balance) {
        super(id,name,age,userName,password,birthday,UserType.ATTENDEE);
        this.gender = gender;
        this.address = address;
        getWallet().setBalance(balance);
    }
    void buyticket(Event event){
        if (event.getNoofattendees() < event.getCapacity()){
            Attendee.super.getWallet().transfer(event.getTicketPrice(), event.getOrganizer().getWallet());
            event.addAttendee(Attendee.this);
            events.add(event);
            System.out.println("Ticket bought successfully");
        }
        else System.out.println("Event is full");




    }



    public Gender getGender() {
        return gender;
    }

        public void setAddress(String address) {
            this.address = address;
        }
        public void updateUserType(){
            setUserType(UserType.ATTENDEE);
        }
        void registerForEvent(Event event){
            if(event.addAttendee(Attendee.this)) {
                System.out.println("Registration successful");
                events.add(event);
            }
        }
        void cancelRegisterForEvent(Event event){
            if (event.cancelRegistration(Attendee.this)) {
                System.out.println("Cancellation successful");
                events.remove(event);
            }
        }

    }
    
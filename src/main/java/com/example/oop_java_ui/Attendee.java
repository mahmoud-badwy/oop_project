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
    public Attendee(int id, String name, int age, String userName, String password, Date birthday, Gender gender, String address, double balance) {
        super(id, name, age, userName, password, birthday, UserType.ATTENDEE);
        this.gender = gender;
        this.address = address;
        this.events = new ArrayList<>();
        getWallet().setBalance(balance);
    }

    public boolean isRegisteredForEvent(Event event) {
        return events.contains(event);
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
            if(event.addAttendee(this)) {
                System.out.println("Registration successful");
                events.add(event);

            }
        }
        void cancelRegisterForEvent(Event event){
            if (event.cancelRegistration(this)) {
                System.out.println("Cancellation successful");
                events.remove(event);
            }
        }

    }
    
package com.example.oop_java_ui;

import java.util.Date;
import java.util.List;

public class Admin extends User {
    private String role;
    private float working_hours;
    private List<Room> rooms;
    private int nextId;

    // Constructor with personal attributes and room manager setup


    public Admin(String role, float working_hours, List<Room> rooms, int nextId, int id, String name, int age, String userName, String password, Date birthday, UserType userType , List<Admin> admin) {
        super(id, name, age, userName, password, birthday, userType);
        this.role = role;
        this.working_hours = working_hours;
       
       
    }
    

    // --- Getters and Setters ---
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(float working_hours) {
        this.working_hours = working_hours;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

   

    public void showEvent()   {}
    public void showattendes()   {}
 
}
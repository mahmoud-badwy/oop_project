package com.example.oop_java_ui;

import java.time.LocalTime;

public class Room {
    private int id;
    private String name;
    private int capacity;
    private TimeSlot availableSlots;
    private boolean isAvailable;

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Room(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.availableSlots = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable(Event event, LocalTime startTime, LocalTime endTime) {
      boolean timeCompare = startTime.isAfter(availableSlots.startTime) && endTime.isBefore(availableSlots.endTime);

      return capacity >= event.getCapacity() && timeCompare;
    }

    public TimeSlot getAvailableSlots() {
        return availableSlots;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", availableSlots=" + availableSlots +
                '}';
    }



    // Inner class for time slots
    public static class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;

        public TimeSlot(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public boolean overlapsWith(TimeSlot other) {
            return !(this.endTime.isBefore(other.startTime) || 
                    this.startTime.isAfter(other.endTime));
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TimeSlot timeSlot = (TimeSlot) obj;
            return startTime.equals(timeSlot.startTime) && 
                   endTime.equals(timeSlot.endTime);
        }

        @Override
        public String toString() {
            return startTime + " - " + endTime;
        }
    }
} 
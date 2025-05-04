package com.example.oop_java_ui;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private final String baseUrl ="C://Users//mahmo//IdeaProjects//oop_java_ui//";
    private  final String USERS_FILE =baseUrl +  "csv_files//users.csv";
    private  final String ROOMS_FILE =baseUrl + "csv_files//rooms.csv";
    private  final String CATEGORIES_FILE =baseUrl + "csv_files//categories.csv";
    private  final String EVENTS_FILE =baseUrl + "csv_files//events.csv";

    // Save Users to CSV
    public  void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("id,name,age,username,password,birthday,userType,isActive,walletBalance");
            for (User user : users) {
                writer.println(String.format("%d,%s,%d,%s,%s,%s,%s,%b,%.2f",
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getBirthday(),
                    user.getUserType(),
                    user.isActive(),
                    user.getWallet().getBalance()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Users from CSV
    public  List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String username = parts[3];
                String password = parts[4];
                Date birthday = new Date(parts[5]);
                UserType userType = UserType.valueOf(parts[6]);
                boolean isActive = Boolean.parseBoolean(parts[7]);
                double walletBalance = Double.parseDouble(parts[8]);

                User user = new User(id, name, age, username, password, birthday, userType) {
                    @Override
                    public String toString() {
                        return "";
                    }
                };
                user.setActive(isActive);
                user.getWallet().setBalance(walletBalance);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Save Rooms to CSV
    public  void saveRooms(List<Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            writer.println("id,name,capacity");
            for (Room room : rooms) {
                writer.println(String.format("%d,%s,%d",
                    room.getId(),
                    room.getName(),
                    room.getCapacity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Rooms from CSV
    public  List<Room> readRooms() {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int capacity = Integer.parseInt(parts[2]);
                rooms.add(new Room(id, name, capacity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Save Categories to CSV
    public  void saveCategories(List<Category> categories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATEGORIES_FILE))) {
            writer.println("id,name,description");
            for (Category category : categories) {
                writer.println(String.format("%d,%s,%s",
                    category.getId(),
                    category.getName(),
                    category.getDescription()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Categories from CSV
    public  List<Category> readCategories() {
        List<Category> categories = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CATEGORIES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String description = parts[2];
                categories.add(new Category(id, name, description));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Save Events to CSV
    public  void saveEvents(List<Event> events) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            writer.println("eventId,eventName,eventDescription,startTime,endTime,ticketPrice,capacity,categoryId,roomId,organizerId");
            for (Event event : events) {
                writer.println(String.format("%d,%s,%s,%s,%s,%.2f,%d,%d,%d,%d",
                    event.getEventId(),
                    event.getEventName(),
                    event.getEventDescription(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getTicketPrice(),
                    event.getCapacity(),
                    event.getCategory().getId(),
                    event.getRoom().getId(),
                    event.getOrganizer().getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Events from CSV
    public  List<Event> readEvents(List<Category> categories, List<Room> rooms, List<User> users) {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int eventId = Integer.parseInt(parts[0]);
                String eventName = parts[1];
                String eventDescription = parts[2];
                LocalTime startTime = LocalTime.parse(parts[3]);
                LocalTime endTime = LocalTime.parse(parts[4]);
                double ticketPrice = Double.parseDouble(parts[5]);
                int capacity = Integer.parseInt(parts[6]);
                int categoryId = Integer.parseInt(parts[7]);
                int roomId = Integer.parseInt(parts[8]);
                int organizerId = Integer.parseInt(parts[9]);

                // Find related objects
                Category category = categories.stream()
                    .filter(c -> c.getId() == categoryId)
                    .findFirst()
                    .orElse(null);
                
                Room room = rooms.stream()
                    .filter(r -> r.getId() == roomId)
                    .findFirst()
                    .orElse(null);
                
                User organizer = users.stream()
                    .filter(u -> u.getId() == organizerId)
                    .findFirst()
                    .orElse(null);

                if (category != null && room != null && organizer != null && organizer instanceof Organizer) {
                    events.add(new Event(eventId, eventName, eventDescription, startTime, endTime,
                        ticketPrice, capacity, category, (Organizer) organizer));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
}

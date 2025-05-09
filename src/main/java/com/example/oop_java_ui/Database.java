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
    private  final String SESSION_FILE =baseUrl + "csv_files//session.csv";

    // Save Users to CSV
    public  void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("id,name,age,username,password,birthday,userType,isActive,walletBalance");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            for (User user : users) {
                writer.println(String.format("%d,%s,%d,%s,%s,%s,%s,%b,%.2f",
                        user.getId(),
                        user.getName(),
                        user.getAge(),
                        user.getUserName(),
                        user.getPassword(),
                        sdf.format(user.getBirthday()),
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
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
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
                java.util.Date birthday;
                try {
                    birthday = sdf.parse(parts[5]);
                } catch (java.text.ParseException e) {
                    birthday = new java.util.Date(); // fallback to current date
                }
                UserType userType = UserType.valueOf(parts[6]);
                boolean isActive = Boolean.parseBoolean(parts[7]);
                double walletBalance = Double.parseDouble(parts[8]);

                User user;
                switch (userType) {
                    case ORGANIZER:
                        user = new Organizer(id, name, age, username, password, birthday, userType);
                        break;
                    case ADMIN:
                        user = new Admin("Administrator", 40.0f, 1, id, name, age, username, password, birthday, userType, new RoomManager());
                        break;
                    case ATTENDEE:
                        user = new Attendee(id, name, age, username, password, birthday, Gender.MALE, "", walletBalance);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected user type: " + userType);
                }
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
    public void saveEvents(List<Event> events) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            writer.println("eventId,eventName,eventDescription,startTime,endTime,ticketPrice,capacity,categoryId,roomId,organizerId,attendeeIds");
            for (Event event : events) {
                int roomId = event.getRoom() != null ? event.getRoom().getId() : -1;
                int categoryId = event.getCategory() != null ? event.getCategory().getId() : -1;
                int organizerId = event.getOrganizer() != null ? event.getOrganizer().getId() : -1;

                // Convert attendee list to comma-separated string of IDs
                String attendeeIds = event.getAttendees().stream()
                        .map(attendee -> String.valueOf(attendee.getId()))
                        .reduce((a, b) -> a + ";" + b)
                        .orElse("");

                writer.println(String.format("%d,%s,%s,%s,%s,%.2f,%d,%d,%d,%d,%s",
                        event.getEventId(),
                        event.getEventName(),
                        event.getEventDescription(),
                        event.getStartTime(),
                        event.getEndTime(),
                        event.getTicketPrice(),
                        event.getCapacity(),
                        categoryId,
                        roomId,
                        organizerId,
                        attendeeIds));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEvent(Event event) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE, true))) {
            int roomId = event.getRoom() != null ? event.getRoom().getId() : -1;
            int categoryId = event.getCategory() != null ? event.getCategory().getId() : -1;
            int organizerId = event.getOrganizer() != null ? event.getOrganizer().getId() : -1;

            // Convert attendee list to comma-separated string of IDs
            String attendeeIds = event.getAttendees().stream()
                    .map(attendee -> String.valueOf(attendee.getId()))
                    .reduce((a, b) -> a + ";" + b)
                    .orElse("");

            writer.println(String.format("%d,%s,%s,%s,%s,%.2f,%d,%d,%d,%d,%s",
                    event.getEventId(),
                    event.getEventName(),
                    event.getEventDescription(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getTicketPrice(),
                    event.getCapacity(),
                    categoryId,
                    roomId,
                    organizerId,
                    attendeeIds));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read Events from CSV
    public List<Event> readEvents() {
        List<Event> events = new ArrayList<>();
        List<User> allUsers = readUsers(); // Get all users to map attendee IDs

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
                String attendeeIdsStr = parts.length > 10 ? parts[10] : "";

                // Create objects with proper null handling
                Category category = categoryId != -1 ? new Category(categoryId, "", "") : null;
                Room room = roomId != -1 ? new Room(roomId, "", 0) : null;
                Organizer organizer = organizerId != -1 ? new Organizer(organizerId, "", 0, "", "", new Date(), UserType.ORGANIZER) : null;

                Event event = new Event(eventId, eventName, eventDescription, startTime, endTime,
                        ticketPrice, capacity, category, organizer);
                if (room != null) {
                    event.setRoom(room);
                }

                // Add attendees if any
                if (!attendeeIdsStr.isEmpty()) {
                    String[] attendeeIds = attendeeIdsStr.split(";");
                    for (String attendeeId : attendeeIds) {
                        int id = Integer.parseInt(attendeeId);
                        allUsers.stream()
                                .filter(user -> user.getId() == id && user instanceof Attendee)
                                .map(user -> (Attendee) user)
                                .findFirst()
                                .ifPresent(event::addAttendee);
                    }
                }

                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    // Update specific user
    public boolean updateUser(User updatedUser) {
        if (updatedUser == null) {
            System.out.println("updateUser: updatedUser is null");
            return false;
        }
        try {
            List<User> users = readUsers();
            if (users.isEmpty()) {
                System.out.println("updateUser: users list is empty after reading CSV");
                return false;
            }
            boolean found = false;
            for (int i = 0; i < users.size(); i++) {
                System.out.println("updateUser: checking user with ID " + users.get(i).getId());
                if (users.get(i).getId() == updatedUser.getId()) {
                    System.out.println("updateUser: found user with ID " + updatedUser.getId());
                    double currentBalance = users.get(i).getWallet().getBalance();
                    updatedUser.getWallet().setBalance(currentBalance);
                    updatedUser.setActive(users.get(i).isActive());
                    users.set(i, updatedUser);
                    found = true;
                    break;
                }
            }
            if (found) {
                saveUsers(users);
                System.out.println("updateUser: user updated and saved");
                deleteSession();
                return true;
            }
            System.out.println("updateUser: user with ID " + updatedUser.getId() + " not found");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update specific room
    public boolean updateRoom(Room updatedRoom) {
        List<Room> rooms = readRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId() == updatedRoom.getId()) {
                rooms.set(i, updatedRoom);
                saveRooms(rooms);
                return true;
            }
        }
        return false;
    }

    // Update specific event
    public boolean updateEvent(Event updatedEvent) {
        if (updatedEvent == null) {
            System.out.println("updateEvent: updatedEvent is null");
            return false;
        }
        try {
            List<Event> events = readEvents();
            if (events.isEmpty()) {
                System.out.println("updateEvent: events list is empty after reading CSV");
                return false;
            }

            boolean found = false;
            for (int i = 0; i < events.size(); i++) {
                System.out.println("updateEvent: checking event with ID " + events.get(i).getEventId());
                if (events.get(i).getEventId() == updatedEvent.getEventId()) {
                    System.out.println("updateEvent: found event with ID " + updatedEvent.getEventId());
                    System.out.println("updateEvent: old event data: " + events.get(i));
                    System.out.println("updateEvent: new event data: " + updatedEvent);
                    events.set(i, updatedEvent);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveEvents(events);
                System.out.println("updateEvent: event updated and saved");
            }
            // Print CSV content after saving (guaranteed after file is closed)
            if (found) {
                try (BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE))) {
                    System.out.println("updateEvent: CSV content after update:");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    System.out.println("updateEvent: error reading CSV after update");
                    e.printStackTrace();
                }
                return true;
            }
            System.out.println("updateEvent: event with ID " + updatedEvent.getEventId() + " not found");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update specific category
    public boolean updateCategory(Category updatedCategory) {
        List<Category> categories = readCategories();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == updatedCategory.getId()) {
                categories.set(i, updatedCategory);
                saveCategories(categories);
                return true;
            }
        }
        return false;
    }

    // Delete room by ID
    public boolean deleteRoom(int roomId) {
        List<Room> rooms = readRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId() == roomId) {
                rooms.remove(i);
                saveRooms(rooms);
                return true;
            }
        }
        return false;
    }

    // Delete event by ID
    public boolean deleteEvent(int eventId) {
        List<Event> events = readEvents();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventId() == eventId) {
                events.remove(i);
                saveEvents(events);
                return true;
            }
        }
        return false;
    }

    // Save Session to CSV
    public void saveSession(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SESSION_FILE))) {
            writer.println("id,name,age,username,password,birthday,userType,isActive,walletBalance");
            writer.println(String.format("%d,%s,%d,%s,%s,%s,%s,%b,%.2f",
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getBirthday(),
                    user.getUserType(),
                    user.isActive(),
                    user.getWallet().getBalance() ) );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //delete session by id
    public void deleteSession() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SESSION_FILE))) {
            // Clear the file by writing only the header
            writer.println("id,name,age,username,password,birthday,userType,isActive,walletBalance");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if session exists
    public Session isSessionExists() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SESSION_FILE))) {
            // Skip header
            reader.readLine();
            // If there's a second line, session exists
            String userData = reader.readLine();
            if (userData != null) {
                String[] parts = userData.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String username = parts[3];
                String password = parts[4];
                Date birthday = new Date();
                UserType userType = UserType.valueOf(parts[6]);
                boolean isActive = Boolean.parseBoolean(parts[7]);
                double walletBalance = Double.parseDouble(parts[8]);

                User user;
                switch (userType) {
                    case ORGANIZER:
                        user = new Organizer(id, name, age, username, password, birthday, userType);
                        break;
                    case ADMIN:
                        user = new Admin("Administrator", 40.0f, 1, id, name, age, username, password, birthday, userType, new RoomManager());
                        break;
                    case ATTENDEE:
                        user = new Attendee(id, name, age, username, password, birthday, Gender.MALE, "", walletBalance);
                        break;
                    default:
                        return new Session(false);
                }
                user.setActive(isActive);
                user.getWallet().setBalance(walletBalance);
                return new Session(user, true);
            }
            return new Session(false);
        } catch (IOException e) {
            e.printStackTrace();
            return new Session(false);
        }
    }

}



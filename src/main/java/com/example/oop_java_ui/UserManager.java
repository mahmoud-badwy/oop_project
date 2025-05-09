package com.example.oop_java_ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class UserManager {
    private List<User> users;
    private int nextId;

    public UserManager() {
        this.users = new ArrayList<>();
        this.nextId = 1;
    }

    // Register a new user
    public User registerUser(String name, int age, String userName, String password, Date birthday, UserType userType) {
        // Check if username already exists
        if (isUsernameTaken(userName)) {
            return null;
        }

        User newUser = new User(nextId++, name, age, userName, password, birthday, userType) {
            @Override
            public String toString() {
                return "";
            }
        };
        users.add(newUser);
        Database db = new Database();
        db.saveUsers(users);
        db.saveSession(newUser);
        return newUser;
    }

    // Login a user
    public User login(String userName, String password) {
        Database db = new Database();
        users = db.readUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.authenticate(password)) {
                users .clear();
                db.saveSession(user);
                return user;
            }
        }
        users .clear();

        return null;
    }

    // Check if username is already taken
    private boolean isUsernameTaken(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    // Get user by ID
    public User getUserById(int id) {
        readAllUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // Get user by username
    public User getUserByUsername(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    // Update user profile
    public boolean updateUserProfile(int id, String name, String username, int age, Date birthday) {
        readAllUsers(); // Ensure users list is up-to-date
        User user = getUserById(id);
        if (user != null) {
            User newUser;
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                newUser = new Admin(
                    admin.getRole(),
                    admin.getWorking_hours(),
                    admin.getNextId(),
                    user.getId(),
                    name,
                    age,
                    username,
                    user.getPassword(),
                    birthday,
                    UserType.ADMIN,
                    admin.roomManager
                );
            } else if (user instanceof Organizer) {
                newUser = new Organizer(
                    user.getId(),
                    name,
                    age,
                    username,
                    user.getPassword(),
                    birthday,
                    UserType.ORGANIZER
                );
            } else if (user instanceof Attendee) {
                Attendee attendee = (Attendee) user;
                newUser = new Attendee(
                    user.getId(),
                    name,
                    age,
                    username,
                    user.getPassword(),
                    birthday,
                    attendee.getGender(),
                    "",
                    attendee.getWallet().getBalance()
                );
            } else {
                return false;
            }
            // Preserve the active status
            newUser.setActive(user.isActive());
            Database db = new Database();
            return db.updateUser(newUser);
        }
        return false;
    }

    // Change password
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        readAllUsers(); // Ensure users list is up-to-date
        User user = getUserById(id);
        if (user != null && user.authenticate(oldPassword)) {
            User newUser;
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                newUser = new Admin(
                    admin.getRole(),
                    admin.getWorking_hours(),
                    admin.getNextId(),
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getUserName(),
                    newPassword,
                    user.getBirthday(),
                    UserType.ADMIN,
                    admin.roomManager
                );
            } else if (user instanceof Organizer) {
                newUser = new Organizer(
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getUserName(),
                    newPassword,
                    user.getBirthday(),
                    UserType.ORGANIZER
                );
            } else if (user instanceof Attendee) {
                Attendee attendee = (Attendee) user;
                newUser = new Attendee(
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getUserName(),
                    newPassword,
                    user.getBirthday(),
                    attendee.getGender(),
                    "",
                    attendee.getWallet().getBalance()
                );
            } else {
                return false;
            }
            
            // Preserve the active status
            newUser.setActive(user.isActive());
            
            Database db = new Database();
            return db.updateUser(newUser);
        }
        return false;
    }

    // Deactivate user account
    public boolean deactivateAccount(int id) {

        User user = getUserById(id);
        if (user != null) {
            user.setActive(false);
            return true;
        }
        return false;
    }

    // Reactivate user account
    public boolean reactivateAccount(int id) {
      
        User user = getUserById(id);
        if (user != null) {
            user.setActive(true);
            return true;
        }
        return false;
    }

    // Get all users (for admin purposes)
    public List<User> getAllUsers() {
        users.clear();
        Database db = new Database();
        users = db.readUsers();
        return new ArrayList<>(users);
    }

    // read all users   
    public void readAllUsers() {
        users.clear();
        Database db = new Database();
        users = db.readUsers();
    }

    // Search users by name
    public List<User> searchUsersByName(String name) {
        readAllUsers();
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(user);
            }
        }
        return results;
    }

    // Get users by type
    public List<User> getUsersByType(UserType userType) {
        readAllUsers();

        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType() == userType) {
                results.add(user);
            }
        }
        return results;
    }

    //logout
    public LogoutResponse logout() {
        try{
            Database db = new Database();
            db.deleteSession();
            return new LogoutResponse(true, "logout successful");
        }catch(Exception e){
            return new LogoutResponse(true, "logout failed");

        }
    }
}

class LogoutResponse{
    private boolean success;
    private String message;
    public LogoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
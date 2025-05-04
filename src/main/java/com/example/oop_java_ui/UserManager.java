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
        return newUser;
    }

    // Login a user
    public User login(String userName, String password) {
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.authenticate(password)) {
                return user;
            }
        }
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
    public boolean updateUserProfile(int id, String name, int age, Date birthday) {
        User user = getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setAge(age);
            user.setBirthday(birthday);
            return true;
        }
        return false;
    }

    // Change password
    public boolean changePassword(int id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        if (user != null && user.authenticate(oldPassword)) {
            user.setPassword(newPassword);
            return true;
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
        return new ArrayList<>(users);
    }

    // Search users by name
    public List<User> searchUsersByName(String name) {
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
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getUserType() == userType) {
                results.add(user);
            }
        }
        return results;
    }
} 
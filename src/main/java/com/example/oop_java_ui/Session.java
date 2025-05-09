package com.example.oop_java_ui;

import java.util.Date;

public class Session {
    private User user;
    private boolean exists;

    public Session(User user, boolean exists) {
        this.user = user;
        this.exists = exists;
    }

    public Session(boolean exists) {
        this.exists = exists;
        this.user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}

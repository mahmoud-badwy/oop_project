package com.example.oop_java_ui;

import java.util.Date;

abstract class User {
    private int id;
    private String name;
    private int age;
    private String userName;
    private String password;
    private Date birthday;
    private UserType userType;
    private boolean isActive;
    private Wallet wallet;

    public User() {
        this.isActive = true;
        this.wallet = new Wallet(0.0);
    }

    public User(int id, String name, int age, String userName, String password, Date birthday, UserType userType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.userType = userType;
        this.isActive = true;
        this.wallet = new Wallet(0.0);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    // Authentication methods
    public boolean authenticate(String password) {
        return this.password.equals(password) && isActive;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (authenticate(oldPassword)) {
            this.password = newPassword;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", userName='" + userName + '\'' +
                ", birthday=" + birthday +
                ", userType=" + userType +
                ", isActive=" + isActive +
                ", walletBalance=" + wallet.getBalance() +
                '}';
    }
}

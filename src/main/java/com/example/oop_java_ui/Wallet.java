package com.example.oop_java_ui;

public class Wallet {

    private double balance;



    public Wallet(double balance) {

        this.balance = balance;
    }
    public Wallet() {

}
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }


    public void deposit(double amount) {
        balance += amount; // Increase the wallet's balance
    }


    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount; // Deduct from balance if sufficient funds
            return true;
        }
        return false; // Not enough balance to withdraw
    }


    public void transfer(double amount, Wallet recipient) {
        if (this.withdraw(amount)) {
            recipient.deposit(amount);
            System.out.println("Transfer successful! " + amount + " has been transferred.");
        } else {
            System.out.println("Transfer failed. Insufficient balance.");
        }
    }
}

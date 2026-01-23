package com.equifax.domain;

public class Account {
    private final int id;
    private final String type;
    private double balance;
    private final double limit;

    public Account(int id, String type, double balance, double limit) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.limit = limit;
    }

    public synchronized double getBalance() { return balance; }
    public double getLimit() { return limit; }

    public synchronized boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (balance < amount) return false;
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return type + " balance=" + balance + "/" + limit;
    }
}

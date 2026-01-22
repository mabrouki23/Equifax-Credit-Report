package com.equifax;

public class Account {
    private int id;
    private String type;
    private double balance;
    private double limit;

    public Account(int id, String type, double balance, double limit) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.limit = limit;
    }

    public double getBalance() { return balance; }
    public double getLimit() { return limit; }

    @Override
    public String toString() {
        return type + " balance=" + balance + "/" + limit;
    }
}
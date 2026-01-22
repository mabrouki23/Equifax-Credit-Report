package com.equifax;

public class Event {
    private String type;
    private double amount;

    public Event(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() { return type; }

    @Override
    public String toString() {
        return type + " : " + amount;
    }
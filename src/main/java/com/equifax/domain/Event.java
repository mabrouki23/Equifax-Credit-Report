package com.equifax.domain;

/**
 * Représente un événement financier lié au client.
 */
public class Event {
    private String type;
    private double amount;

    public Event(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return type + " : " + amount;
    }
}

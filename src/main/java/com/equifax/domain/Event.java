package com.equifax.domain;

import java.time.LocalDate;

/**
 * Représente un événement financier affectant le dossier de crédit d'un client
 */
public class Event {
    public enum EventType {
        BAD_CHEQUE, ACCOUNT_CLOSED, BANKRUPTCY, DEBT_COLLECTION_AGENCY
    }

    private String id;
    private LocalDate date;
    private EventType type;
    private double amount;

    public Event(String id, LocalDate date, EventType type, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public EventType getType() { return type; }
    public void setType(EventType type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
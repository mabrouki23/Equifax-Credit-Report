package com.equifax;

import java.util.*;

/**
 * Représente un client du bureau de crédit.
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private List<Account> accounts = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<LenderInquiry> inquiries = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private double creditScore = 600;

    public Client(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() { return lastName; }
    public List<Account> getAccounts() { return accounts; }
    public List<Event> getEvents() { return events; }
    public List<LenderInquiry> getInquiries() { return inquiries; }
    public List<String> getMessages() { return messages; }
    public double getCreditScore() { return creditScore; }
    public void setCreditScore(double score) { this.creditScore = score; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | CreditScore=" + creditScore;
    }
}
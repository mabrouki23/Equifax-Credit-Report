package com.equifax.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Représente un client du bureau de crédit.
 */
public class Client {
    private final int id;
    private final String firstName;
    private final String lastName;
    private String address;
    private final List<Account> accounts = Collections.synchronizedList(new ArrayList<>());
    private final List<Event> events = Collections.synchronizedList(new ArrayList<>());
    private final List<LenderInquiry> inquiries = Collections.synchronizedList(new ArrayList<>());
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());
    private double creditScore = 600;

    public Client(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Account> getAccounts() { return accounts; }
    public List<Event> getEvents() { return events; }
    public List<LenderInquiry> getInquiries() { return inquiries; }
    public List<String> getMessages() { return messages; }
    public synchronized double getCreditScore() { return creditScore; }
    public synchronized void setCreditScore(double score) { this.creditScore = score; }

    public void addAccount(Account a) { accounts.add(a); }
    public void addEvent(Event e) { events.add(e); }
    public void addInquiry(LenderInquiry li) { inquiries.add(li); }
    public void addMessage(String m) { messages.add(m); }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | CreditScore=" + creditScore;
    }
}

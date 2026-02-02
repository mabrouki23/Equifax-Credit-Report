package com.equifax.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un client enregistré auprès du bureau de crédit.
 * Cette classe gère les informations personnelles du client,
 * ses comptes financiers, événements, demandes de crédit et son score de crédit.
 *
 * @author Système Equifax
 * @version 1.0
 */
public class Client {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private List<Account> accounts;
    private List<Event> events;
    private List<LenderInquiry> inquiries;
    private List<String> messages;
    private double creditScore;

    /**
     * Constructeur pour créer un nouveau client
     * @param id Identifiant unique du client
     * @param firstName Prénom du client
     * @param lastName Nom de famille du client
     * @param address Adresse du client
     */
    public Client(String id, String firstName, String lastName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.accounts = new ArrayList<>();
        this.events = new ArrayList<>();
        this.inquiries = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.creditScore = 600.0; // Score de base
    }

    /**
     * Ajoute un compte au client
     * @param a Le compte à ajouter
     */
    public void addAccount(Account a) {
        if (a != null && !accounts.contains(a)) {
            accounts.add(a);
        }
    }

    /**
     * Ajoute un événement financier au client
     * @param e L'événement à ajouter
     */
    public void addEvent(Event e) {
        if (e != null && !events.contains(e)) {
            events.add(e);
        }
    }

    /**
     * Ajoute une demande de crédit au client
     * @param li La demande de crédit à ajouter
     */
    public void addInquiry(LenderInquiry li) {
        if (li != null && !inquiries.contains(li)) {
            inquiries.add(li);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", creditScore=" + creditScore +
                ", accounts=" + accounts.size() +
                ", events=" + events.size() +
                ", inquiries=" + inquiries.size() +
                '}';
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
    public List<LenderInquiry> getInquiries() { return inquiries; }
    public void setInquiries(List<LenderInquiry> inquiries) { this.inquiries = inquiries; }
    public List<String> getMessages() { return messages; }
    public void setMessages(List<String> messages) { this.messages = messages; }
    public double getCreditScore() { return creditScore; }
    public void setCreditScore(double creditScore) { this.creditScore = creditScore; }
}
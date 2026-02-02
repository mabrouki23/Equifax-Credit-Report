package com.equifax.domain;

import java.time.LocalDate;

/**
 * Représente une demande de consultation du dossier de crédit par un prêteur
 */
public class LenderInquiry {
    private String lender;
    private LocalDate date;
    private Client client;
    private double amount;

    public LenderInquiry(String lender, LocalDate date, Client client, double amount) {
        this.lender = lender;
        this.date = date;
        this.client = client;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "LenderInquiry{" +
                "lender='" + lender + '\'' +
                ", date=" + date +
                ", client=" + (client != null ? client.getId() : "null") +
                ", amount=" + amount +
                '}';
    }

    // Getters et Setters
    public String getLender() { return lender; }
    public void setLender(String lender) { this.lender = lender; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
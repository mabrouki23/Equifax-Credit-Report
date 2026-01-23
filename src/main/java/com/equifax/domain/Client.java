package com.equifax.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Représente un client enregistré auprès du bureau de crédit.
 * <p>
 * Un client possède :
 * <ul>
 *   <li>un identifiant {@code id},</li>
 *   <li>un prénom {@code firstName} et un nom {@code lastName},</li>
 *   <li>une adresse {@code address},</li>
 *   <li>une liste de comptes {@code accounts},</li>
 *   <li>une liste d'événements financiers {@code events},</li>
 *   <li>une liste de demandes de crédit {@code inquiries},</li>
 *   <li>une liste de messages/notifications {@code messages},</li>
 *   <li>un score de crédit {@code creditScore}.</li>
 * </ul>
 *
 * Les collections sont thread-safe (synchronisées) pour supporter des accès concurrents
 * depuis les services et threads (recalcul de score, notifications, opérations sur comptes).
 */
@SuppressWarnings("unused")
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

    /**
     * Construit un client avec un identifiant et un nom.
     *
     * @param id identifiant unique
     * @param firstName prénom
     * @param lastName nom de famille
     */
    public Client(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Retourne l'identifiant du client.
     */
    public int getId() { return id; }

    /**
     * Retourne le prénom du client.
     */
    public String getFirstName() { return firstName; }

    /**
     * Retourne le nom de famille du client.
     */
    public String getLastName() { return lastName; }

    /**
     * Retourne l'adresse du client.
     */
    public String getAddress() { return address; }

    /**
     * Définit ou met à jour l'adresse du client.
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Retourne la liste des comptes du client.
     * <p>La liste est synchronisée pour usage concurrent.
     */
    public List<Account> getAccounts() { return accounts; }

    /**
     * Retourne la liste des événements financiers du client.
     */
    public List<Event> getEvents() { return events; }

    /**
     * Retourne la liste des demandes de crédit du client.
     */
    public List<LenderInquiry> getInquiries() { return inquiries; }

    /**
     * Retourne la liste des messages/notifications du client.
     */
    public List<String> getMessages() { return messages; }

    /**
     * Obtient le score de crédit (accès synchronisé pour sécurité concurrente).
     *
     * @return valeur du creditScore
     */
    public synchronized double getCreditScore() { return creditScore; }

    /**
     * Met à jour le score de crédit du client (accès synchronisé).
     *
     * @param score nouveau score à affecter
     */
    public synchronized void setCreditScore(double score) { this.creditScore = score; }

    /**
     * Ajoute un compte au client et assigne le propriétaire du compte.
     *
     * @param a compte à ajouter
     */
    public void addAccount(Account a) {
        a.setClient(this);
        accounts.add(a);
    }

    /**
     * Ajoute un événement financier au client.
     *
     * @param e événement
     */
    public void addEvent(Event e) { events.add(e); }

    /**
     * Ajoute une demande de crédit au client.
     *
     * @param li demande
     */
    public void addInquiry(LenderInquiry li) { inquiries.add(li); }

    /**
     * Ajoute un message / notification au client.
     *
     * @param m message
     */
    public void addMessage(String m) { messages.add(m); }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | CreditScore=" + creditScore;
    }
}

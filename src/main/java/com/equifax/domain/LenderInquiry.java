package com.equifax.domain;

import java.time.LocalDate;

/**
 * Représente une demande de consultation du dossier de crédit faite par un prêteur.
 * <p>
 * Une instance contient :
 * <ul>
 *   <li>le nom du prêteur ({@code lender}),</li>
 *   <li>la date de la demande ({@code date}),</li>
 *   <li>le client concerné ({@code client}),</li>
 *   <li>le montant demandé ({@code amount}).</li>
 * </ul>
 *
 * Cette classe est immuable (les champs sont en lecture seule après construction).
 */
@SuppressWarnings("unused")
public class LenderInquiry {
    private final String lender;
    private final LocalDate date;
    private final Client client;
    private final double amount;

    /**
     * Constructeur complet.
     *
     * @param lender nom de l'institution ou du prêteur
     * @param date date de la demande
     * @param client client concerné
     * @param amount montant demandé
     */
    public LenderInquiry(String lender, LocalDate date, Client client, double amount) {
        this.lender = lender;
        this.date = date;
        this.client = client;
        this.amount = amount;
    }

    /**
     * Constructeur de commodité qui utilise la date du jour.
     *
     * @param lender nom du prêteur
     * @param client client concerné
     * @param amount montant demandé
     */
    public LenderInquiry(String lender, Client client, double amount) {
        this(lender, LocalDate.now(), client, amount);
    }

    /**
     * Retourne le nom du prêteur.
     *
     * @return lender
     */
    public String getLender() { return lender; }

    /**
     * Retourne la date de la demande.
     *
     * @return date (LocalDate)
     */
    public LocalDate getDate() { return date; }

    /**
     * Retourne le client concerné par la demande.
     *
     * @return client
     */
    public Client getClient() { return client; }

    /**
     * Retourne le montant demandé.
     *
     * @return amount
     */
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "LenderInquiry{" +
                "lender='" + lender + '\'' +
                ", date=" + date +
                ", client=" + (client != null ? client.getFirstName() + " " + client.getLastName() : "null") +
                ", amount=" + amount +
                '}';
    }
}

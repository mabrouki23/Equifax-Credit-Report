package com.equifax.domain;

import java.time.LocalDate;

/**
 * Représente un événement financier enregistré pour un client.
 * <p>
 * Un événement contient :
 * <ul>
 *   <li>un identifiant unique {@code id},</li>
 *   <li>la {@link LocalDate date} de survenue,</li>
 *   <li>un {@code type} (ex. BAD_CHEQUE, ACCOUNT_CLOSED, BANKRUPTCY, DEBT_COLLECTION_AGENCY),</li>
 *   <li>un {@code amount} représentant le montant impliqué par l'événement.</li>
 * </ul>
 * Cette classe est immuable après construction.
 *
 * Usage typique :
 * <pre>
 *   Event e = new Event(1, LocalDate.of(2024, 4, 1), "BAD_CHEQUE", 150.0);
 * </pre>
 *
 * Les instances peuvent être utilisées par les services pour calculer des pénalités
 * lors du calcul du credit score d'un client.
 *
 */
@SuppressWarnings("unused")
public class Event {
    private final int id;
    private final LocalDate date;
    private final String type;
    private final double amount;

    /**
     * Constructeur complet.
     *
     * @param id     identifiant unique de l'événement
     * @param date   date de survenue de l'événement (ne doit pas être {@code null})
     * @param type   type d'événement (valeur textuelle, ex. "BANKRUPTCY")
     * @param amount montant associé à l'événement
     */
    public Event(int id, LocalDate date, String type, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    /**
     * Constructeur utilitaire qui assigne la date courante.
     *
     * @param id     identifiant unique
     * @param type   type d'événement
     * @param amount montant associé
     */
    public Event(int id, String type, double amount) {
        this(id, LocalDate.now(), type, amount);
    }

    /**
     * Retourne l'identifiant de l'événement.
     *
     * @return id
     */
    public int getId() { return id; }

    /**
     * Retourne la date de survenue de l'événement.
     *
     * @return date (LocalDate)
     */
    public LocalDate getDate() { return date; }

    /**
     * Retourne le type de l'événement.
     *
     * @return type (String)
     */
    public String getType() { return type; }

    /**
     * Retourne le montant associé à l'événement.
     *
     * @return montant (double)
     */
    public double getAmount() { return amount; }

    /**
     * Représentation textuelle lisible de l'événement.
     *
     * @return chaîne décrivant l'événement
     */
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }
}

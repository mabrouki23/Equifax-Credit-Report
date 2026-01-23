package com.equifax.domain;

/**
 * Représente un compte financier appartenant à un client.
 * Fournit des opérations thread-safe de dépôt et de retrait.
 */
@SuppressWarnings("unused")
public class Account {
    private final int id;
    private final String type;
    private double balance;
    private final double limit;
    private Client client; // propriétaire du compte

    /**
     * Construit un compte.
     * @param id identifiant du compte
     * @param type type de compte (chequing, savings, credit card, ...)
     * @param balance solde initial
     * @param limit limite de crédit
     */
    public Account(int id, String type, double balance, double limit) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.limit = limit;
    }

    /**
     * Récupère le solde courant (thread-safe).
     */
    public synchronized double getBalance() { return balance; }

    /**
     * Récupère la limite du compte.
     */
    public double getLimit() { return limit; }

    public int getId() { return id; }
    public String getType() { return type; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    /**
     * Dépose un montant sur le compte (synchronisé).
     * @return true si l'opération a réussi
     */
    public synchronized boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    /**
     * Retire un montant du compte (synchronisé).
     * @return true si le retrait a pu être effectué
     */
    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (balance < amount) return false;
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        String owner = (client != null) ? (client.getFirstName() + " " + client.getLastName()) : "no-owner";
        return String.format("Account{id=%d, type=%s, balance=%.2f, limit=%.2f, owner=%s}", id, type, balance, limit, owner);
    }
}

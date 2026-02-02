package com.equifax.domain;

/**
 * Représente un compte financier appartenant à un client
 */
public class Account {
    public enum AccountType {
        CHEQUING, SAVINGS, CREDIT_CARD, STORE_CARD, LINE_OF_CREDIT, LOAN
    }

    private String id;
    private AccountType type;
    private double balance;
    private double limit;
    private Client client;

    public Account(String id, AccountType type, double balance, double limit, Client client) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.limit = limit;
        this.client = client;
    }

    /**
     * Dépose un montant dans le compte
     * @param amount Montant à déposer
     * @return true si le dépôt est réussi
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Retire un montant du compte
     * @param amount Montant à retirer
     * @return true si le retrait est réussi
     */
    public boolean withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                ", limit=" + limit +
                ", client=" + (client != null ? client.getId() : "null") +
                '}';
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public double getLimit() { return limit; }
    public void setLimit(double limit) { this.limit = limit; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}
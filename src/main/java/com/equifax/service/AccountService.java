package com.equifax.service;

import com.equifax.domain.Account;
import com.equifax.domain.Client;

/**
 * Service pour gérer les comptes des clients
 */
public class AccountService {

    /**
     * Ajoute un compte à un client
     * @param client Le client
     * @param account Le compte à ajouter
     */
    public void addAccount(Client client, Account account) {
        if (client != null && account != null) {
            client.addAccount(account);
        }
    }

    /**
     * Retire un compte d'un client
     * @param client Le client
     * @param account Le compte à retirer
     */
    public void removeAccount(Client client, Account account) {
        if (client != null && account != null) {
            client.getAccounts().remove(account);
        }
    }

    /**
     * Effectue un dépôt dans un compte
     * @param account Le compte
     * @param amount Le montant à déposer
     * @return true si le dépôt est réussi
     */
    public boolean deposit(Account account, double amount) {
        if (account != null) {
            return account.deposit(amount);
        }
        return false;
    }

    /**
     * Effectue un retrait d'un compte
     * @param account Le compte
     * @param amount Le montant à retirer
     * @return true si le retrait est réussi
     */
    public boolean withdraw(Account account, double amount) {
        if (account != null) {
            return account.withdraw(amount);
        }
        return false;
    }
}
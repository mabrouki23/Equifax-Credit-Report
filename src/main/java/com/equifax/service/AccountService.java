package com.equifax.service;

import com.equifax.domain.Account;
import com.equifax.domain.Client;

/**
 * Service pour la gestion des comptes des clients.
 * <p>
 * Fournit des opérations de haut niveau sur les comptes tels que l'ajout,
 * la suppression, les dépôts et retraits. Les opérations de modification
 * du solde délèguent la synchronisation au niveau de l'objet {@link Account}.
 */
public class AccountService {
    /**
     * Ajoute un compte au client.
     *
     * @param client  client auquel ajouter le compte
     * @param account compte à ajouter
     */
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    /**
     * Supprime un compte d'un client.
     *
     * @param client  client dont il faut retirer le compte
     * @param account compte à retirer
     */
    public void removeAccount(Client client, Account account) {
        client.getAccounts().remove(account);
    }

    /**
     * Effectue un dépôt sur un compte.
     *
     * Cette méthode délègue la synchronisation à {@link Account#deposit(double)}.
     * Elle retourne {@code true} si l'opération a réussi (montant > 0), sinon {@code false}.
     *
     * @param account compte cible
     * @param amount  montant à déposer (doit être > 0)
     * @return {@code true} si dépôt réussi, {@code false} sinon
     */
    public boolean deposit(Account account, double amount) {
        return account.deposit(amount);
    }

    /**
     * Effectue un retrait sur un compte.
     *
     * Cette méthode délègue la synchronisation à {@link Account#withdraw(double)}.
     * Elle retourne {@code true} si le retrait a été autorisé et exécuté, sinon {@code false}.
     *
     * @param account compte cible
     * @param amount  montant à retirer (doit être > 0)
     * @return {@code true} si retrait effectué, {@code false} sinon
     */
    public boolean withdraw(Account account, double amount) {
        return account.withdraw(amount);
    }
}

package com.equifax.service;

import com.equifax.domain.Account;
import com.equifax.domain.Client;
import com.equifax.domain.Event;
import com.equifax.domain.LenderInquiry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les opérations sur les clients
 */
public class ClientService {

    /**
     * Recherche les clients par nom de famille en utilisant une expression lambda
     * @param clients Liste de clients
     * @param lastName Nom de famille à rechercher
     * @return Liste des clients correspondants
     */
    public List<Client> searchClientByLastName(List<Client> clients, String lastName) {
        return clients.stream()
                .filter(c -> c.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    /**
     * Calcule le total des balances de tous les comptes d'un client
     * @param client Le client
     * @return Le total des balances
     */
    public double calculateTotalBalance(Client client) {
        return client.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    /**
     * Met à jour le score de crédit d'un client selon l'algorithme défini
     * @param client Le client dont le score doit être mis à jour
     */
    public void updateCreditScore(Client client) {
        double score = 600.0; // Score de base

        // Bonus pour nombre de comptes (calculé en premier)
        int accountCount = client.getAccounts().size();
        if (accountCount > 3) {
            score += 20;
        } else if (accountCount == 0) {
            score -= 50;
        }

        // Analyse des comptes
        double totalBalance = 0;
        double totalLimit = 0;

        for (Account account : client.getAccounts()) {
            totalBalance += account.getBalance();
            totalLimit += account.getLimit();
        }

        // Calcul du ratio d'utilisation du crédit
        if (totalLimit > 0) {
            double utilizationRatio = totalBalance / totalLimit;
            if (utilizationRatio < 0.3) {
                score += 50; // Faible utilisation
            } else if (utilizationRatio > 0.7) {
                score -= 50; // Utilisation élevée
            }
        }

        // Analyse des événements financiers
        for (Event event : client.getEvents()) {
            switch (event.getType()) {
                case BAD_CHEQUE:
                    score -= 30;
                    break;
                case ACCOUNT_CLOSED:
                    score -= 20;
                    break;
                case BANKRUPTCY:
                    score -= 150;
                    break;
                case DEBT_COLLECTION_AGENCY:
                    score -= 100;
                    break;
            }
        }

        // Analyse des demandes de crédit
        int inquiryCount = client.getInquiries().size();
        if (inquiryCount > 5) {
            score -= (inquiryCount - 5) * 10; // Pénalité pour trop de demandes
        }

        // Limiter le score entre 300 et 900
        score = Math.max(300, Math.min(900, score));

        client.setCreditScore(score);
    }

    /**
     * Envoie une notification à un client
     * @param client Le client destinataire
     * @param message Le message à envoyer
     */
    public void sendNotification(Client client, String message) {
        client.getMessages().add(message);
    }
}
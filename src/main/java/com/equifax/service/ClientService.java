package com.equifax.service;

import com.equifax.domain.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Service pour la gestion des clients : recherche, totalisation des comptes,
 * calcul du credit score et envoi de notifications.
 */
public class ClientService {
    private static final double BASE_SCORE = 600;

    /**
     * Recherche les clients dont le nom de famille correspond (insensible à la casse).
     * Utilise une expression lambda pour filtrer la liste.
     * @param clients liste de clients à rechercher
     * @param lastName nom de famille recherché
     * @return liste des clients trouvés
     */
    public List<Client> searchClientByLastName(List<Client> clients, String lastName) {
        return clients.stream()
                .filter(c -> c.getLastName() != null && c.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    /**
     * Calcule la somme des soldes de tous les comptes du client.
     * @param client client
     * @return total des soldes
     */
    public double calculateTotalBalance(Client client) {
        return client.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    /**
     * Calcule la limite totale de crédit du client.
     */
    public double calculateTotalLimit(Client client) {
        return client.getAccounts().stream()
                .mapToDouble(Account::getLimit)
                .sum();
    }

    /**
     * Calcule le ratio d'utilisation du crédit.
     */
    public double calculateUtilizationRatio(Client client) {
        double totalLimit = calculateTotalLimit(client);
        if (totalLimit <= 0) return 0.0;
        return calculateTotalBalance(client) / totalLimit;
    }

    private double calculateEventPenalty(Client client) {
        double penalty = 0.0;
        for (Event e : client.getEvents()) {
            String t = e.getType();
            switch (t) {
                case "BANKRUPTCY" -> penalty += 150 + 0.05 * e.getAmount();
                case "BAD_CHEQUE" -> penalty += 50 + 0.02 * e.getAmount();
                case "ACCOUNT_CLOSED" -> penalty += 30;
                case "DEBT_COLLECTION_AGENCY" -> penalty += 100 + 0.03 * e.getAmount();
                default -> penalty += 20; // other minor events
            }
        }
        return penalty;
    }

    private double calculateInquiryPenalty(Client client) {
        double penalty = 0.0;
        LocalDate now = LocalDate.now();
        for (LenderInquiry li : client.getInquiries()) {
            Period p = Period.between(li.getDate(), now);
            int days = p.getDays() + p.getMonths() * 30 + p.getYears() * 365;
            if (days <= 90) penalty += 10;
            else if (days <= 365) penalty += 5;
        }
        return penalty;
    }

    private double calculateAccountBonus(Client client) {
        int count = client.getAccounts().size();
        double bonus = count * 15; // 15 points per account
        boolean hasCredit = client.getAccounts().stream()
                .anyMatch(a -> a.getType().toLowerCase().contains("credit"));
        boolean hasSavings = client.getAccounts().stream()
                .anyMatch(a -> a.getType().toLowerCase().contains("savings") || a.getType().toLowerCase().contains("chequing"));
        if (hasCredit && hasSavings) bonus += 10; // diversity
        if (count == 0) bonus -= 50; // penalty if no account
        return bonus;
    }

    /**
     * Met à jour le creditScore du client selon comptes, événements et demandes.
     * Le score final est lissé puis borné entre 300 et 900.
     * @param client client à mettre à jour
     */
    public void updateCreditScore(Client client) {
        double prev = client.getCreditScore();
        double score = BASE_SCORE;

        double utilization = calculateUtilizationRatio(client);
        double utilPenalty = utilization * 300;

        double eventPenalty = calculateEventPenalty(client);
        double inquiryPenalty = calculateInquiryPenalty(client);
        double accountBonus = calculateAccountBonus(client);

        score = score - utilPenalty - eventPenalty - inquiryPenalty + accountBonus;

        // smoothing
        score = prev * 0.25 + score * 0.75;

        // clamp
        score = Math.max(300, Math.min(900, score));

        client.setCreditScore(score);
    }

    /**
     * Envoie une notification au client (ajoute au champ messages).
     * @param client client destinataire
     * @param message contenu du message
     */
    public void sendNotification(Client client, String message) {
        client.addMessage(message);
    }
}

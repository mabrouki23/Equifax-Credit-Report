package com.equifax;

public class ClientService {

    public double calculateTotalBalance(Client client) {
        return client.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public void updateCreditScore(Client client) {
        double score = 600;
        score -= client.getEvents().size() * 50;
        score -= client.getInquiries().size() * 10;
        score += client.getAccounts().size() * 20;
        score = Math.max(300, Math.min(900, score));
        client.setCreditScore(score);
    }
}
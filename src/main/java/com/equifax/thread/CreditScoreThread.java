package com.equifax.thread;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;

/**
 * Thread de recalcul du credit score.
 */
public class CreditScoreThread extends Thread {
    private Client client;
    private ClientService service;

    public CreditScoreThread(Client client, ClientService service) {
        this.client = client;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            service.updateCreditScore(client);
        } catch (Exception e) {
            System.err.println("Erreur lors du recalcul du score: " + e.getMessage());
        }
    }
}

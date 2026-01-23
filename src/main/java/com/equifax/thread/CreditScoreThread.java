package com.equifax.thread;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;

/**
 * Thread qui recalcul le creditScore d'un client en arrière-plan.
 */
public class CreditScoreThread extends Thread {
    private final Client client;
    private final ClientService service;

    public CreditScoreThread(Client client, ClientService service) {
        this.client = client;
        this.service = service;
    }

    /**
     * Exécute la mise à jour du score de crédit via le service.
     */
    @Override
    public void run() {
        try {
            service.updateCreditScore(client);
        } catch (Exception e) {
            System.err.println("Erreur lors du recalcul du score: " + e.getMessage());
        }
    }
}

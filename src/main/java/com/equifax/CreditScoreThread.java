package com.equifax;

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
        service.updateCreditScore(client);
    }
}
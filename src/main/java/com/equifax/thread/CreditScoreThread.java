package com.equifax.thread;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;

/**
 * Thread pour recalculer le score de crédit d'un client en parallèle.
 * Cette classe permet d'exécuter le calcul du score sans bloquer le programme principal.
 *
 * @author Système Equifax
 * @version 1.0
 */
public class CreditScoreThread extends Thread {
    private Client client;
    private ClientService clientService;

    /**
     * Constructeur du thread de calcul de score
     * @param client Le client dont le score doit être calculé
     * @param clientService Le service client pour effectuer le calcul
     */
    public CreditScoreThread(Client client, ClientService clientService) {
        this.client = client;
        this.clientService = clientService;
    }

    /**
     * Méthode exécutée lors du démarrage du thread.
     * Effectue le recalcul du score de crédit du client.
     */
    @Override
    public void run() {
        System.out.println("Début du calcul du score de crédit pour le client: " +
                client.getFirstName() + " " + client.getLastName());

        clientService.updateCreditScore(client);

        System.out.println("Calcul terminé. Nouveau score: " + client.getCreditScore());
    }

    // Getters
    public Client getClient() { return client; }
    public ClientService getClientService() { return clientService; }
}
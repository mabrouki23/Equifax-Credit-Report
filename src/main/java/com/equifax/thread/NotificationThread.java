package com.equifax.thread;

import com.equifax.domain.Client;

/**
 * Thread pour envoyer des notifications aux clients en parallèle.
 * Cette classe permet d'envoyer des notifications sans bloquer le programme principal.
 *
 * @author Système Equifax
 * @version 1.0
 */
public class NotificationThread extends Thread {
    private Client client;
    private String message;

    /**
     * Constructeur du thread de notification
     * @param client Le client destinataire
     * @param message Le message à envoyer
     */
    public NotificationThread(Client client, String message) {
        this.client = client;
        this.message = message;
    }

    /**
     * Méthode exécutée lors du démarrage du thread.
     * Ajoute le message à la liste des messages du client et affiche la notification.
     */
    @Override
    public void run() {
        client.getMessages().add(message);

        System.out.println("===========================================");
        System.out.println("NOTIFICATION pour " + client.getFirstName() +
                " " + client.getLastName());
        System.out.println("Message: " + message);
        System.out.println("===========================================");
    }

    // Getters
    public Client getClient() { return client; }
    public String getMessage() { return message; }
}
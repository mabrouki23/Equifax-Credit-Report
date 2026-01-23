package com.equifax.thread;

import com.equifax.domain.Client;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Thread de notification client.
 * Produit un message formaté et l'ajoute à la liste des messages du client.
 * Format : "Notification - ID client={id} | Nom={firstName} {lastName} | TypeEvenement={type} | Heure={yyyy-MM-dd HH:mm:ss} | Message={message}"
 */
public class NotificationThread extends Thread {
    private final Client client;
    private final String message;
    private final String eventType;

    public NotificationThread(Client client, String message) {
        this(client, null, message);
    }

    public NotificationThread(Client client, String eventType, String message) {
        this.client = client;
        this.message = message;
        this.eventType = eventType;
    }

    /**
     * Construit et enregistre la notification pour le client.
     */
    @Override
    public void run() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String evt = (eventType == null || eventType.isEmpty()) ? "GENERAL" : eventType;
        String formatted = String.format("Notification - ID client=%d | Nom=%s %s | TypeEvenement=%s | Heure=%s | Message=%s",
                client.getId(), client.getFirstName(), client.getLastName(), evt, time, message);
        client.addMessage(formatted);
        System.out.println(formatted);
    }
}

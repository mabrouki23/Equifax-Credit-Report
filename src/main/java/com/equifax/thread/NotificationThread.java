package com.equifax.thread;

import com.equifax.domain.Client;

/**
 * Thread de notification client.
 */
public class NotificationThread extends Thread {
    private Client client;
    private String message;

    public NotificationThread(Client client, String message) {
        this.client = client;
        this.message = message;
    }

    @Override
    public void run() {
        client.addMessage(message);
        System.out.println("Notification: " + message);
    }
}

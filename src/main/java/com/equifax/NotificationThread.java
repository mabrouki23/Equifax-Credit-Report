package com.equifax;

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
        client.getMessages().add(message);
        System.out.println("Notification: " + message);
    }
}
package com.equifax;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(1, "Ali", "Benali");
        client.getAccounts().add(new Account(1, "Credit Card", 2000, 5000));
        client.getEvents().add(new Event("BANKRUPTCY", 1000));
        client.getInquiries().add(new LenderInquiry("Bank A"));

        ClientService service = new ClientService();
        CreditScoreThread t1 = new CreditScoreThread(client, service);
        NotificationThread t2 = new NotificationThread(client, "Score mis à jour");

        t1.start();
        t1.join();
        t2.start();

        System.out.println(client);
    }
}
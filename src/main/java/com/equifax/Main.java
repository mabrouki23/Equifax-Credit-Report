package com.equifax;

import com.equifax.domain.Client;
import com.equifax.domain.Account;
import com.equifax.domain.Event;
import com.equifax.domain.LenderInquiry;
import com.equifax.service.ClientService;
import com.equifax.thread.CreditScoreThread;
import com.equifax.thread.NotificationThread;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client(1, "Ali", "Benali");
        client.addAccount(new Account(1, "Credit Card", 2000, 5000));
        client.addAccount(new Account(2, "Savings", 1500, 0));
        client.addEvent(new Event("BANKRUPTCY", 1000));
        client.addInquiry(new LenderInquiry("Bank A"));

        ClientService service = new ClientService();

        // Recherche par nom (exemple avec lambda)
        List<Client> found = service.searchClientByLastName(List.of(client), "Benali");
        System.out.println("Recherche par nom: " + found);

        System.out.println("Total balance: " + service.calculateTotalBalance(client));

        CreditScoreThread t1 = new CreditScoreThread(client, service);
        NotificationThread t2 = new NotificationThread(client, "Score mis à jour");

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        System.out.println(client);
    }
}
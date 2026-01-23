package com.equifax;

import com.equifax.domain.Client;
import com.equifax.domain.Account;
import com.equifax.domain.Event;
import com.equifax.domain.LenderInquiry;
import com.equifax.domain.Creditor;
import com.equifax.service.ClientService;
import com.equifax.thread.CreditScoreThread;
import com.equifax.thread.NotificationThread;

import java.util.List;

/**
 * Exemple d'utilisation minimale du système de rapport de crédit.
 */
public class Main {
    static void main(String[] args) throws InterruptedException {
        // utiliser args pour éviter l'avertissement 'never used'
        if (args != null && args.length > 0) {
            System.out.println("Arguments fournis: " + args.length);
        }

        Client client = new Client(1, "Ali", "Benali");
        client.setAddress("123 Rue Principale");
        Account acc1 = new Account(1, "Credit Card", 2000, 5000);
        Account acc2 = new Account(2, "Savings", 1500, 0);
        client.addAccount(acc1);
        client.addAccount(acc2);
        client.addEvent(new Event(1, "BANKRUPTCY", 1000));
        client.addInquiry(new LenderInquiry("Bank A", client, 5000));

        ClientService service = new ClientService();

        // Recherche par nom (lambda)
        List<Client> found = service.searchClientByLastName(List.of(client), "Benali");
        System.out.println("Recherche par nom: " + found);

        // Total des balances
        System.out.println("Total balance: " + service.calculateTotalBalance(client));

        // afficher les ids des comptes pour utiliser getId()
        client.getAccounts().forEach(a -> System.out.println("Account id: " + a.getId()));

        // Recalcule du score dans un thread séparé
        CreditScoreThread t1 = new CreditScoreThread(client, service);
        t1.start();
        t1.join();

        // envoyer aussi une notification via ClientService
        service.sendNotification(client, "Notification par le service: votre score a été recalculé.");

        // Envoi d'une notification en français
        NotificationThread t2 = new NotificationThread(client, "MISE_A_JOUR_SCORE", "Votre score a été recalculé.");
        t2.start();
        t2.join();

        // utiliser Creditor pour éviter warning 'never used'
        Creditor creditor = new Creditor(1, "Banque X", "1 Rue", "SWFT123");
        creditor.addClient(client);
        System.out.println("Recherche chez creditor: " + creditor.searchClientByLastName("Benali"));

        System.out.println(client);
        client.getMessages().forEach(System.out::println);
    }
}
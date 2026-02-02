package com.equifax;

import com.equifax.domain.*;
import com.equifax.service.*;
import com.equifax.thread.CreditScoreThread;
import com.equifax.thread.NotificationThread;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale pour démontrer le fonctionnement du système Equifax
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SYSTÈME EQUIFAX - DÉMONSTRATION ===\n");

        // Initialisation des services
        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();
        EventService eventService = new EventService();
        LenderInquiryService inquiryService = new LenderInquiryService();

        // Création d'un créancier
        Creditor creditor = new Creditor("C001", "Banque Nationale",
                "500 Place d'Armes, Montréal", "BNDCCAMMXXX");

        // Création de clients
        Client client1 = new Client("CL001", "Jean", "Tremblay", "123 Rue Sherbrooke, Montréal");
        Client client2 = new Client("CL002", "Marie", "Tremblay", "456 Rue St-Denis, Montréal");
        Client client3 = new Client("CL003", "Pierre", "Dubois", "789 Boulevard René-Lévesque, Montréal");

        creditor.addClient(client1);
        creditor.addClient(client2);
        creditor.addClient(client3);

        // Ajout de comptes pour client1
        Account account1 = new Account("A001", Account.AccountType.CHEQUING, 2500, 0, client1);
        Account account2 = new Account("A002", Account.AccountType.SAVINGS, 15000, 0, client1);
        Account account3 = new Account("A003", Account.AccountType.CREDIT_CARD, 1500, 5000, client1);

        accountService.addAccount(client1, account1);
        accountService.addAccount(client1, account2);
        accountService.addAccount(client1, account3);

        // Ajout de comptes pour client2
        Account account4 = new Account("A004", Account.AccountType.CHEQUING, 1000, 0, client2);
        Account account5 = new Account("A005", Account.AccountType.CREDIT_CARD, 4500, 5000, client2);

        accountService.addAccount(client2, account4);
        accountService.addAccount(client2, account5);

        // Ajout d'événements négatifs pour client2
        Event event1 = new Event("E001", LocalDate.now().minusMonths(3),
                Event.EventType.BAD_CHEQUE, 150);
        Event event2 = new Event("E002", LocalDate.now().minusMonths(1),
                Event.EventType.ACCOUNT_CLOSED, 0);

        eventService.addEvent(client2, event1);
        eventService.addEvent(client2, event2);

        // Ajout de demandes de crédit
        LenderInquiry inquiry1 = new LenderInquiry("Banque TD", LocalDate.now().minusDays(15),
                client1, 20000);
        LenderInquiry inquiry2 = new LenderInquiry("Desjardins", LocalDate.now().minusDays(5),
                client2, 5000);
        LenderInquiry inquiry3 = new LenderInquiry("Banque Scotia", LocalDate.now().minusDays(3),
                client2, 10000);

        inquiryService.addInquiry(client1, inquiry1);
        inquiryService.addInquiry(client2, inquiry2);
        inquiryService.addInquiry(client2, inquiry3);

        System.out.println("=== 1. RECHERCHE DE CLIENTS PAR NOM ===");
        List<Client> allClients = new ArrayList<>();
        allClients.add(client1);
        allClients.add(client2);
        allClients.add(client3);

        List<Client> tremblayClients = clientService.searchClientByLastName(allClients, "Tremblay");
        System.out.println("Clients avec le nom 'Tremblay': " + tremblayClients.size());
        tremblayClients.forEach(c -> System.out.println("  - " + c));
        System.out.println();

        System.out.println("=== 2. CALCUL DU TOTAL DES BALANCES ===");
        double totalBalance1 = clientService.calculateTotalBalance(client1);
        double totalBalance2 = clientService.calculateTotalBalance(client2);

        System.out.println("Balance totale de " + client1.getFirstName() + " " +
                client1.getLastName() + ": " + totalBalance1 + "$");
        System.out.println("Balance totale de " + client2.getFirstName() + " " +
                client2.getLastName() + ": " + totalBalance2 + "$");
        System.out.println();

        System.out.println("=== 3. MISE À JOUR DU SCORE DE CRÉDIT (AVEC THREADS) ===");
        System.out.println("Score initial de " + client1.getFirstName() + ": " +
                client1.getCreditScore());
        System.out.println("Score initial de " + client2.getFirstName() + ": " +
                client2.getCreditScore());
        System.out.println();

        // Création et démarrage des threads pour calculer les scores
        CreditScoreThread scoreThread1 = new CreditScoreThread(client1, clientService);
        CreditScoreThread scoreThread2 = new CreditScoreThread(client2, clientService);

        scoreThread1.start();
        scoreThread2.start();

        // Attendre la fin des calculs
        try {
            scoreThread1.join();
            scoreThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("=== 4. ENVOI DE NOTIFICATIONS (AVEC THREADS) ===");

        // Création et démarrage des threads pour envoyer les notifications
        NotificationThread notifThread1 = new NotificationThread(client1,
                "Votre score de crédit a été mis à jour. Nouveau score: " + client1.getCreditScore());
        NotificationThread notifThread2 = new NotificationThread(client2,
                "Votre score de crédit a été mis à jour. Nouveau score: " + client2.getCreditScore());

        notifThread1.start();
        notifThread2.start();

        // Attendre la fin des notifications
        try {
            notifThread1.join();
            notifThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("=== 5. AFFICHAGE DES RÉSULTATS FINAUX ===");
        System.out.println("\n--- Client 1 ---");
        System.out.println(client1);
        System.out.println("Nombre de comptes: " + client1.getAccounts().size());
        System.out.println("Nombre d'événements: " + client1.getEvents().size());
        System.out.println("Nombre de demandes: " + client1.getInquiries().size());
        System.out.println("Messages reçus: " + client1.getMessages().size());

        System.out.println("\n--- Client 2 ---");
        System.out.println(client2);
        System.out.println("Nombre de comptes: " + client2.getAccounts().size());
        System.out.println("Nombre d'événements: " + client2.getEvents().size());
        System.out.println("Nombre de demandes: " + client2.getInquiries().size());
        System.out.println("Messages reçus: " + client2.getMessages().size());

        System.out.println("\n=== DÉMONSTRATION TERMINÉE ===");
    }
}
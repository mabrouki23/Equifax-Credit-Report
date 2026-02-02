package com.equifax.service;

import com.equifax.domain.Account;
import com.equifax.domain.Client;
import com.equifax.domain.Event;
import com.equifax.domain.LenderInquiry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ClientService avec mocking
 * Compatible avec JUnit 5 et Mockito pour Java 25
 *
 * IMPORTANT: Ce fichier doit être placé dans src/test/java/com/equifax/service/
 */
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private ClientService clientService;

    @Mock
    private Client mockClient;

    @BeforeEach
    public void setUp() {
        clientService = new ClientService();
    }

    /**
     * Test de recherche de clients par nom de famille
     */
    @Test
    public void testSearchClientByLastName() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("1", "Jean", "Tremblay", "123 Rue A"));
        clients.add(new Client("2", "Marie", "Tremblay", "456 Rue B"));
        clients.add(new Client("3", "Pierre", "Dubois", "789 Rue C"));

        // Act
        List<Client> result = clientService.searchClientByLastName(clients, "Tremblay");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getLastName().equals("Tremblay")));
    }

    /**
     * Test de recherche avec aucun résultat
     */
    @Test
    public void testSearchClientByLastNameNoResult() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("1", "Jean", "Tremblay", "123 Rue A"));

        // Act
        List<Client> result = clientService.searchClientByLastName(clients, "Martin");

        // Assert
        assertEquals(0, result.size());
    }

    /**
     * Test du calcul du total des balances
     */
    @Test
    public void testCalculateTotalBalance() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        client.addAccount(new Account("A1", Account.AccountType.CHEQUING, 1000, 0, client));
        client.addAccount(new Account("A2", Account.AccountType.SAVINGS, 2500, 0, client));
        client.addAccount(new Account("A3", Account.AccountType.CREDIT_CARD, 500, 5000, client));

        // Act
        double totalBalance = clientService.calculateTotalBalance(client);

        // Assert
        assertEquals(4000.0, totalBalance, 0.01);
    }

    /**
     * Test du calcul du score de crédit avec mock
     */
    @Test
    public void testUpdateCreditScoreWithMock() {
        // Arrange
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("A1", Account.AccountType.CHEQUING, 1000, 5000, mockClient));

        List<Event> events = new ArrayList<>();
        List<LenderInquiry> inquiries = new ArrayList<>();

        when(mockClient.getAccounts()).thenReturn(accounts);
        when(mockClient.getEvents()).thenReturn(events);
        when(mockClient.getInquiries()).thenReturn(inquiries);

        // Act
        clientService.updateCreditScore(mockClient);

        // Assert
        verify(mockClient).setCreditScore(anyDouble());
        verify(mockClient, atLeastOnce()).getAccounts();
        verify(mockClient, atLeastOnce()).getEvents();
        verify(mockClient, atLeastOnce()).getInquiries();
    }

    /**
     * Test du calcul du score de crédit avec événements négatifs
     */
    @Test
    public void testUpdateCreditScoreWithNegativeEvents() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        client.addAccount(new Account("A1", Account.AccountType.CHEQUING, 1000, 5000, client));
        client.addEvent(new Event("E1", LocalDate.now(), Event.EventType.BAD_CHEQUE, 200));
        client.addEvent(new Event("E2", LocalDate.now(), Event.EventType.BANKRUPTCY, 0));

        double initialScore = client.getCreditScore();

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertTrue(client.getCreditScore() < initialScore,
                "Le score devrait diminuer avec des événements négatifs");
    }

    /**
     * Test d'envoi de notification
     */
    @Test
    public void testSendNotification() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        String message = "Votre score de crédit a été mis à jour";

        // Act
        clientService.sendNotification(client, message);

        // Assert
        assertTrue(client.getMessages().contains(message));
        assertEquals(1, client.getMessages().size());
    }

    /**
     * Test du calcul du score avec limite supérieure
     */
    @Test
    public void testCreditScoreUpperLimit() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        // Ajouter plusieurs comptes avec faible utilisation
        for (int i = 0; i < 5; i++) {
            client.addAccount(new Account("A" + i, Account.AccountType.SAVINGS, 100, 10000, client));
        }

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertTrue(client.getCreditScore() <= 900,
                "Le score ne devrait pas dépasser 900");
    }

    /**
     * Test du calcul du score avec limite inférieure
     */
    @Test
    public void testCreditScoreLowerLimit() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        // Ajouter plusieurs événements négatifs
        client.addEvent(new Event("E1", LocalDate.now(), Event.EventType.BANKRUPTCY, 0));
        client.addEvent(new Event("E2", LocalDate.now(), Event.EventType.DEBT_COLLECTION_AGENCY, 5000));
        client.addEvent(new Event("E3", LocalDate.now(), Event.EventType.BAD_CHEQUE, 200));

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertTrue(client.getCreditScore() >= 300,
                "Le score ne devrait pas être inférieur à 300");
    }

    /**
     * Test de recherche avec mock - vérifie que la méthode utilise bien le lastName
     */
    @Test
    public void testSearchClientByLastNameWithMock() {
        // Arrange
        when(mockClient.getLastName()).thenReturn("Tremblay");

        List<Client> clients = new ArrayList<>();
        clients.add(mockClient);
        clients.add(new Client("2", "Pierre", "Dubois", "789 Rue C"));

        // Act
        List<Client> result = clientService.searchClientByLastName(clients, "Tremblay");

        // Assert
        assertEquals(1, result.size(), "Devrait trouver 1 client");
        verify(mockClient, atLeastOnce()).getLastName();
    }

    /**
     * Test du calcul du total avec mock complet
     */
    @Test
    public void testCalculateTotalBalanceWithMock() {
        // Arrange
        List<Account> mockAccounts = new ArrayList<>();
        Account mockAccount1 = mock(Account.class);
        Account mockAccount2 = mock(Account.class);

        when(mockAccount1.getBalance()).thenReturn(1000.0);
        when(mockAccount2.getBalance()).thenReturn(2500.0);

        mockAccounts.add(mockAccount1);
        mockAccounts.add(mockAccount2);

        when(mockClient.getAccounts()).thenReturn(mockAccounts);

        // Act
        double total = clientService.calculateTotalBalance(mockClient);

        // Assert
        assertEquals(3500.0, total, 0.01, "Le total devrait être 3500");
        verify(mockClient).getAccounts();
        verify(mockAccount1).getBalance();
        verify(mockAccount2).getBalance();
    }

    /**
     * Test du calcul du score avec plusieurs demandes de crédit
     */
    @Test
    public void testCreditScoreWithManyInquiries() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        client.addAccount(new Account("A1", Account.AccountType.CHEQUING, 1000, 5000, client));

        // Calcul du score de référence avec 1 compte et faible utilisation
        // Score de base: 600
        // +20 pour 1 compte (non, car <= 3)
        // +50 pour faible utilisation (1000/5000 = 20%)
        // Score attendu sans demandes: 650
        double expectedScoreWithoutInquiries = 650.0;

        // Ajouter 8 demandes de crédit (plus de 5)
        for (int i = 0; i < 8; i++) {
            client.addInquiry(new LenderInquiry("Banque" + i, LocalDate.now(), client, 5000));
        }

        // Pénalité pour 8 demandes: (8 - 5) * 10 = -30 points
        double expectedScore = expectedScoreWithoutInquiries - 30;

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertEquals(expectedScore, client.getCreditScore(), 0.01,
                "Le score devrait être " + expectedScore + " avec 8 demandes de crédit");
        assertTrue(client.getCreditScore() < expectedScoreWithoutInquiries,
                "Le score devrait diminuer avec beaucoup de demandes");
    }

    /**
     * Test du calcul du score avec utilisation élevée du crédit
     */
    @Test
    public void testCreditScoreWithHighUtilization() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        // Balance élevée par rapport à la limite (80% d'utilisation)
        client.addAccount(new Account("A1", Account.AccountType.CREDIT_CARD, 4000, 5000, client));

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertTrue(client.getCreditScore() < 600,
                "Le score devrait diminuer avec une utilisation élevée");
    }

    /**
     * Test du calcul du score avec faible utilisation du crédit
     */
    @Test
    public void testCreditScoreWithLowUtilization() {
        // Arrange
        Client client = new Client("1", "Jean", "Tremblay", "123 Rue A");
        // Balance faible par rapport à la limite (20% d'utilisation)
        client.addAccount(new Account("A1", Account.AccountType.CREDIT_CARD, 1000, 5000, client));

        // Act
        clientService.updateCreditScore(client);

        // Assert
        assertTrue(client.getCreditScore() > 600,
                "Le score devrait augmenter avec une faible utilisation");
    }
}
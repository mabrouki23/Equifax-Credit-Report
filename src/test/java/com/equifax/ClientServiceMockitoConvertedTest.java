package com.equifax;

import com.equifax.domain.Account;
import com.equifax.domain.Client;
import com.equifax.service.ClientService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientServiceMockitoConvertedTest {

    @Test
    public void testCalculateTotalBalanceWithRealObjects() {
        // create two real accounts
        Account a1 = new Account(1, "Checking", 100.0, 0);
        Account a2 = new Account(2, "Credit Card", 200.0, 1000.0);

        // real client
        Client client = new Client(1, "Real", "Client");
        client.addAccount(a1);
        client.addAccount(a2);

        ClientService service = new ClientService();
        double total = service.calculateTotalBalance(client);
        assertEquals(300.0, total, 1e-6);
    }

    @Test
    public void testUpdateCreditScoreChangesScore() {
        // create a real client
        Client client = new Client(10, "Real", "User");

        // give it some accounts/events/inquiries to influence score
        Account a = new Account(1, "Credit Card", 100.0, 1000.0);
        client.addAccount(a);
        // use Event with id
        client.addEvent(new com.equifax.domain.Event(1, "BAD_CHEQUE", 100));
        client.addInquiry(new com.equifax.domain.LenderInquiry("LenderX", client, 5000));

        ClientService service = new ClientService();
        double before = client.getCreditScore();
        service.updateCreditScore(client);
        double after = client.getCreditScore();

        assertTrue(after >= 300 && after <= 900, "score must be clamped between 300 and 900");
        assertTrue(after != before, "score should change after updateCreditScore");
    }
}

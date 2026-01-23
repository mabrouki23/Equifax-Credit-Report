package com.equifax;

import com.equifax.domain.Client;
import com.equifax.domain.Account;
import com.equifax.domain.Event;
import com.equifax.domain.LenderInquiry;
import com.equifax.service.ClientService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    @Test
    public void testCalculateTotalBalance() {
        Client c = new Client(1, "John", "Doe");
        c.addAccount(new Account(1, "Checking", 100.0, 0));
        c.addAccount(new Account(2, "Credit Card", 200.0, 1000));

        ClientService service = new ClientService();
        double total = service.calculateTotalBalance(c);
        assertEquals(300.0, total, 0.0001);
    }

    @Test
    public void testUpdateCreditScoreBounds() {
        Client c = new Client(2, "Jane", "Smith");
        // add lots of negative events to push score down
        for (int i = 0; i < 10; i++) {
            c.addEvent(new Event("BANKRUPTCY", 10000));
        }
        // add many inquiries
        for (int i = 0; i < 20; i++) {
            c.addInquiry(new LenderInquiry("Lender" + i));
        }

        ClientService service = new ClientService();
        service.updateCreditScore(c);
        double score = c.getCreditScore();
        assertTrue(score >= 300 && score <= 900, "Score doit être dans les bornes 300-900");
    }
}

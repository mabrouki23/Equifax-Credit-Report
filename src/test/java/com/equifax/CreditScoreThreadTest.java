package com.equifax;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;
import com.equifax.thread.CreditScoreThread;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditScoreThreadTest {

    @Test
    public void testRunCallsUpdate() throws InterruptedException {
        AtomicBoolean called = new AtomicBoolean(false);

        ClientService service = new ClientService() {
            @Override
            public void updateCreditScore(com.equifax.domain.Client client) {
                called.set(true);
            }
        };

        Client client = new Client(1, "T", "User");

        CreditScoreThread t = new CreditScoreThread(client, service);
        t.start();
        t.join(); // attendre la fin

        assertTrue(called.get(), "updateCreditScore should have been called");
    }
}

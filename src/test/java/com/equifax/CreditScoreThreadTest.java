package com.equifax;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;
import com.equifax.thread.CreditScoreThread;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditScoreThreadTest {

    @Test
    public void testRunCallsUpdate() {
        AtomicBoolean called = new AtomicBoolean(false);

        ClientService service = new ClientService() {
            @Override
            public void updateCreditScore(com.equifax.domain.Client client) {
                called.set(true);
            }

            // keep other methods default by delegating to super if needed
        };

        Client client = new Client(1, "T", "User");

        CreditScoreThread t = new CreditScoreThread(client, service);
        t.run(); // call directly to avoid thread scheduling issues

        assertTrue(called.get(), "updateCreditScore should have been called");
    }
}

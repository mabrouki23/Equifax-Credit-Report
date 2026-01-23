package com.equifax;

import com.equifax.domain.Account;
import com.equifax.domain.Client;
import com.equifax.thread.NotificationThread;
import com.equifax.service.AccountService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrencyTest {

    private static class AutoCloseableExecutor implements AutoCloseable {
        private final ExecutorService es;
        private final long timeoutSeconds;

        AutoCloseableExecutor(ExecutorService es, long timeoutSeconds) {
            this.es = es;
            this.timeoutSeconds = timeoutSeconds;
        }

        ExecutorService get() { return es; }

        @Override
        public void close() {
            es.shutdown();
            try {
                boolean terminated = es.awaitTermination(timeoutSeconds, TimeUnit.SECONDS);
                if (!terminated) {
                    es.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                es.shutdownNow();
            }
        }
    }

    @Test
    public void testConcurrentDepositsWithdrawalsAndNotifications() throws InterruptedException {
        Client client = new Client(1, "Concurrent", "User");
        Account account = new Account(1, "Checking", 1000.0, 0);
        client.addAccount(account);

        AccountService accountService = new AccountService();

        int threads = 10;
        try (AutoCloseableExecutor ace = new AutoCloseableExecutor(Executors.newFixedThreadPool(threads), 10)) {
            ExecutorService es = ace.get();

            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch done = new CountDownLatch(threads);

            for (int i = 0; i < threads; i++) {
                final int idx = i;
                es.submit(() -> {
                    try {
                        start.await();
                        for (int k = 0; k < 100; k++) {
                            accountService.deposit(account, 10);
                            accountService.withdraw(account, 5);
                        }
                        // send notification in a separate thread but join to ensure delivery (French message)
                        String eventType = "EVENEMENT_" + idx;
                        String fullMsg = "Transaction terminée par le thread " + idx;
                        NotificationThread nt = new NotificationThread(client, eventType, fullMsg);
                        nt.start();
                        try {
                            nt.join();
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                });
            }

            start.countDown();
            done.await();
        }

        // After all deposits: each thread does (100 * ( +10 -5)) = +500 per thread -> 10 threads => +5000
        double expected = 1000.0 + threads * 500.0;
        assertEquals(expected, account.getBalance(), 1e-6);

        // Check notifications count and content (French format)
        assertEquals(threads, client.getMessages().size());
        client.getMessages().forEach(m -> {
            assertTrue(m.contains("ID client=" + client.getId()));
            assertTrue(m.contains("TypeEvenement=EVENEMENT_"));
            assertTrue(m.contains("Message=Transaction terminée par le thread "));
            assertTrue(m.contains("Nom=" + client.getFirstName()));
        });
    }
}

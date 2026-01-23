package com.equifax;

import com.equifax.domain.Client;
import com.equifax.service.ClientService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceMockitoTest {

    @Test
    public void testSearchClientByLastName() {
        Client c1 = new Client(1, "A", "Dupont");
        Client c2 = new Client(2, "B", "Martin");

        ClientService service = new ClientService();
        var res = service.searchClientByLastName(List.of(c1, c2), "Dupont");
        assertEquals(1, res.size());
        assertEquals("Dupont", res.get(0).getLastName());
    }
}

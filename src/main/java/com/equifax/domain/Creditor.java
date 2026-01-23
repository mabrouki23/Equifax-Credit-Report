package com.equifax.domain;

import java.util.*;

public class Creditor {
    private int id;
    private String name;
    private String address;
    private String swiftCode;
    private List<Client> clients = new ArrayList<>();

    public Creditor(int id, String name, String address, String swiftCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.swiftCode = swiftCode;
    }

    public void addClient(Client c) { clients.add(c); }
    public void removeClient(Client c) { clients.remove(c); }

    public List<Client> searchClientByLastName(String lastName) {
        return clients.stream()
                .filter(c -> c.getLastName() != null && c.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    @Override
    public String toString() {
        return name + " (" + swiftCode + ")";
    }
}

package com.equifax.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Représente une institution financière (banque, organisme de crédit, etc.)
 */
public class Creditor {
    private String id;
    private String name;
    private String address;
    private String swiftCode;
    private List<Client> clients;

    public Creditor(String id, String name, String address, String swiftCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.swiftCode = swiftCode;
        this.clients = new ArrayList<>();
    }

    public void addClient(Client c) {
        if (c != null && !clients.contains(c)) {
            clients.add(c);
        }
    }

    public void removeClient(Client c) {
        clients.remove(c);
    }

    /**
     * Recherche les clients par nom de famille
     * @param lastName Le nom de famille à rechercher
     * @return Liste des clients correspondants
     */
    public List<Client> searchClientByLastName(String lastName) {
        return clients.stream()
                .filter(c -> c.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Creditor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", swiftCode='" + swiftCode + '\'' +
                ", clients=" + clients.size() +
                '}';
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getSwiftCode() { return swiftCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }
    public List<Client> getClients() { return clients; }
    public void setClients(List<Client> clients) { this.clients = clients; }
}
package com.equifax.domain;

import java.util.*;

/**
 * Représente un créancier (institution financière) avec une liste de clients rattachés.
 */
@SuppressWarnings("unused")
public class Creditor {
    private final int id;
    private final String name;
    private final String address;
    private final String swiftCode;
    private final List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    /**
     * Construit un créancier.
     * @param id identifiant unique
     * @param name nom
     * @param address adresse
     * @param swiftCode code SWIFT
     */
    public Creditor(int id, String name, String address, String swiftCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.swiftCode = swiftCode;
    }

    /**
     * Ajoute un client au créancier.
     * @param c client
     */
    public void addClient(Client c) { clients.add(c); }

    /**
     * Retire un client du créancier.
     * @param c client
     */
    public void removeClient(Client c) { clients.remove(c); }

    /**
     * Recherche les clients rattachés par nom de famille (insensible à la casse).
     * Utilise une expression lambda pour le filtrage.
     * @param lastName nom recherché
     * @return liste des clients correspondants
     */
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

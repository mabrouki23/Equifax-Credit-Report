package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des événements financiers attachés aux clients.
 */
public class EventService {
    /**
     * Ajoute un événement au client.
     */
    public void addEvent(Client client, Event event) {
        client.addEvent(event);
    }

    /**
     * Récupère les événements d'un client filtrés par type.
     */
    public List<Event> getEventsByType(Client client, String type) {
        return client.getEvents().stream()
                .filter(e -> e.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    /**
     * Récupère tous les événements d'un client.
     */
    public List<Event> getAllEvents(Client client) {
        return client.getEvents();
    }
}

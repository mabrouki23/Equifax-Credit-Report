package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.Event;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les événements financiers
 */
public class EventService {

    /**
     * Ajoute un événement à un client
     * @param client Le client
     * @param event L'événement à ajouter
     */
    public void addEvent(Client client, Event event) {
        if (client != null && event != null) {
            client.addEvent(event);
        }
    }

    /**
     * Récupère les événements d'un client par type
     * @param client Le client
     * @param type Le type d'événement
     * @return Liste des événements du type spécifié
     */
    public List<Event> getEventsByType(Client client, String type) {
        if (client == null || type == null) {
            return List.of();
        }

        try {
            Event.EventType eventType = Event.EventType.valueOf(type.toUpperCase());
            return client.getEvents().stream()
                    .filter(e -> e.getType() == eventType)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    /**
     * Récupère tous les événements d'un client
     * @param client Le client
     * @return Liste de tous les événements
     */
    public List<Event> getAllEvents(Client client) {
        if (client == null) {
            return List.of();
        }
        return client.getEvents();
    }
}
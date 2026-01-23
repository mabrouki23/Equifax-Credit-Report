package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {
    public void addEvent(Client client, Event event) {
        client.addEvent(event);
    }

    public List<Event> getEventsByType(Client client, String type) {
        return client.getEvents().stream()
                .filter(e -> e.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Event> getAllEvents(Client client) {
        return client.getEvents();
    }
}

package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.LenderInquiry;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des demandes de consultation de crédit (inquiries).
 */
public class LenderInquiryService {
    /**
     * Ajoute une demande au client.
     */
    public void addInquiry(Client client, LenderInquiry inquiry) {
        client.addInquiry(inquiry);
    }

    /**
     * Récupère les demandes récentes (en nombre de jours).
     */
    public List<LenderInquiry> getRecentInquiries(Client client, int days) {
        LocalDate now = LocalDate.now();
        return client.getInquiries().stream()
                .filter(i -> {
                    Period p = Period.between(i.getDate(), now);
                    int d = p.getDays() + p.getMonths()*30 + p.getYears()*365;
                    return d <= days;
                })
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les demandes d'un client.
     */
    public List<LenderInquiry> getAllInquiries(Client client) {
        return client.getInquiries();
    }
}

package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.LenderInquiry;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les demandes de crédit
 */
public class LenderInquiryService {

    /**
     * Ajoute une demande de crédit à un client
     * @param client Le client
     * @param inquiry La demande de crédit à ajouter
     */
    public void addInquiry(Client client, LenderInquiry inquiry) {
        if (client != null && inquiry != null) {
            client.addInquiry(inquiry);
        }
    }

    /**
     * Récupère les demandes de crédit récentes d'un client
     * @param client Le client
     * @param days Nombre de jours pour définir "récent"
     * @return Liste des demandes récentes
     */
    public List<LenderInquiry> getRecentInquiries(Client client, int days) {
        if (client == null || days < 0) {
            return List.of();
        }

        LocalDate cutoffDate = LocalDate.now().minusDays(days);

        return client.getInquiries().stream()
                .filter(inquiry -> inquiry.getDate().isAfter(cutoffDate) ||
                        inquiry.getDate().isEqual(cutoffDate))
                .collect(Collectors.toList());
    }

    /**
     * Récupère toutes les demandes de crédit d'un client
     * @param client Le client
     * @return Liste de toutes les demandes
     */
    public List<LenderInquiry> getAllInquiries(Client client) {
        if (client == null) {
            return List.of();
        }
        return client.getInquiries();
    }
}
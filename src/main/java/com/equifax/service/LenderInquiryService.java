package com.equifax.service;

import com.equifax.domain.Client;
import com.equifax.domain.LenderInquiry;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class LenderInquiryService {
    public void addInquiry(Client client, LenderInquiry inquiry) {
        client.addInquiry(inquiry);
    }

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

    public List<LenderInquiry> getAllInquiries(Client client) {
        return client.getInquiries();
    }
}

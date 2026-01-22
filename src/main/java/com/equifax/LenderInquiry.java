package com.equifax;

import java.time.LocalDate;

public class LenderInquiry {
    private String lender;
    private LocalDate date;

    public LenderInquiry(String lender) {
        this.lender = lender;
        this.date = LocalDate.now();
    }

    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return lender + " on " + date;
    }
}
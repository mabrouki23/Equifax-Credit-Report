package com.equifax.service;

import com.equifax.domain.Account;
import com.equifax.domain.Client;

public class AccountService {
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    public void removeAccount(Client client, Account account) {
        client.getAccounts().remove(account);
    }

    public boolean deposit(Account account, double amount) {
        return account.deposit(amount);
    }

    public boolean withdraw(Account account, double amount) {
        return account.withdraw(amount);
    }
}

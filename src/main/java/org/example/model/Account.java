package org.example.model;

import java.util.Objects;

public class Account {
    private int accountId;
    private int userId;
    private int balance;
    private String currency;

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && userId == account.userId && balance == account.balance && Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, balance, currency);
    }
}

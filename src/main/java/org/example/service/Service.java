package org.example.service;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.User;

import java.util.Scanner;

public class Service {
    public User inputUser() {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name: ");
        String nameUser = scanner.nextLine();
        if (nameUser.length() != 0) {
            user.setName(nameUser);
            System.out.println("Enter user address: ");
            user.setAddress(scanner.nextLine());
            return user;
        } else System.out.println("username not entered!");
        return null;
    }

    public Account inputAccount() {
        Account account = new Account();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account balance: ");
        account.setBalance(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter account currency in the format: \n - USD \n - EUR \n - BYN \n - RUB");
        String currency = scanner.nextLine();
        currency = currency.toUpperCase();
        if (currency.equals("USD") || currency.equals("EUR") || currency.equals("BYN") || currency.equals("RUB")) {
            account.setCurrency(currency);
            return account;
        } else {
            System.out.println("wrong currency!");
            return null;
        }
    }

    public Transaction inputTransaction() {
        Transaction transaction = new Transaction();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter amount: ");
        int amount = Integer.parseInt(scanner.nextLine());
        if (amount < 100000000) {
            transaction.setAmount(amount);
            return transaction;
        } else {
            System.out.println("transaction limit exceeded");
            return null;
        }
    }
}

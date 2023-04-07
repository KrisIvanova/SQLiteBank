package org.example;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.query_executor.QueryExecutor;
import org.example.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class BankAppDB {

    private static final String DATA_BASE_URL =
            "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\bankdb.db";

    public static void main(String[] args) {
        QueryExecutor queryExecutor = new QueryExecutor();
        Service service = new Service();

        if (isDriverExists()) {
            try {
                Connection connection = DriverManager.getConnection(DATA_BASE_URL);
                String action;
                do {
                    printMenu();
                    action = new Scanner(System.in).nextLine();
                    switch (action) {
                        case "1":
                            queryExecutor.printAllUsers(connection);
                            break;
                        case "2":
                            User user = service.inputUser();
                            if (user != null) {
                                queryExecutor.addUser(connection, user);
                            }
                            break;
                        case "3":
                            System.out.println("Enter id user");
                            int idUser = new Scanner(System.in).nextInt();
                            if (queryExecutor.isUserExists(connection, idUser)) {
                                Account account = service.inputAccount();
                                if (account != null) {
                                    queryExecutor.addAccount(connection, idUser, account);
                                }
                            }
                            break;
                        case "4":
                            System.out.println("Enter id account for top up");
                            int idAccountForTopUp = new Scanner(System.in).nextInt();
                            if (queryExecutor.isAccountExists(connection, idAccountForTopUp)) {
                                Transaction transaction = service.inputTransaction();
                                if (transaction != null) {
                                    queryExecutor.topUpAccount(connection, transaction, idAccountForTopUp);
                                }
                            }
                            break;
                        case "5":
                            System.out.println("Enter id account for withdraw");
                            int idAccountForWithdraw = new Scanner(System.in).nextInt();
                            if (queryExecutor.isAccountExists(connection, idAccountForWithdraw)) {
                                Transaction transaction = service.inputTransaction();
                                if (transaction != null) {
                                    queryExecutor.withdrawFromAccount(connection, transaction, idAccountForWithdraw);
                                }
                            }
                            break;
                        case "6":
                            System.out.println("Enter id account for delete");
                            int idAccountForDelete = new Scanner(System.in).nextInt();
                            queryExecutor.deleteAccount(connection, idAccountForDelete);
                            break;
                        case "7":
                            System.out.println("Enter id user for delete");
                            int idUserForDelete = new Scanner(System.in).nextInt();
                            queryExecutor.deleteUser(connection, idUserForDelete);
                            break;
                    }
                } while (!"8".equals(action));
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL was not found");
            }
        }
    }

    public static boolean isDriverExists() {
        try {
            Class.forName("org.sqlite.JDBC");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver was not found");
            return false;
        }
    }

    private static void printMenu() {
        System.out.println("select operation: ");
        System.out.println("1 - show all users");
        System.out.println("2 - add new user");
        System.out.println("3 - add new account for user");
        System.out.println("4 - topUp account for user");
        System.out.println("5 - withdraw account for user");
        System.out.println("6 - delete account");
        System.out.println("7 - delete user");
        System.out.println("8 - exit");
    }
}

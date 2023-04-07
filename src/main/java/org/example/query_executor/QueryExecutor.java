package org.example.query_executor;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.User;

import java.sql.*;

public class QueryExecutor {
    public void printAllUsers(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()) {
                System.out.println("userId: " + resultSet.getInt("userId"));
                System.out.println("name:" + resultSet.getString("name"));
                System.out.println("address: " + resultSet.getString("address"));
            }
            statement.close();
            resultSet.close();

            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Accounts;");
            while (resultSet.next()) {
                System.out.println("accountId: " + resultSet.getInt("accountId"));
                System.out.println("userId:" + resultSet.getInt("userId"));
                System.out.println("balance: " + resultSet.getInt("balance"));
                System.out.println("currency: " + resultSet.getString("currency"));
            }
            statement2.close();
            resultSet2.close();
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public void addUser(Connection connection, User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Users (name, address) VALUES (?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getAddress());
            preparedStatement.execute();
            preparedStatement.close();
            System.out.println("user added successfully!");
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public void addAccount(Connection connection, int idUser, Account account) {
        if (isCurrencyExists(connection, idUser, account)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO Accounts (userId, balance, currency) VALUES (?, ?, ?)");

                preparedStatement.setInt(1, idUser);
                preparedStatement.setInt(2, account.getBalance());
                preparedStatement.setString(3, account.getCurrency());
                preparedStatement.execute();
                preparedStatement.close();
                System.out.println("account added successfully!");
            } catch (SQLException e) {
                System.out.println("SQL was not found");
            }
        } else System.out.println("an account in this currency already exists!");
    }

    public void topUpAccount(Connection connection, Transaction transaction, int idAccountForTopUp) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Accounts");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Accounts SET balance=? WHERE accountId=?");
            while (resultSet.next()) {
                if (resultSet.getInt("accountId") == idAccountForTopUp) {
                    int finalBalance = resultSet.getInt("balance") + transaction.getAmount();
                    if (checkBalanceForExcess(finalBalance)) {
                        preparedStatement.setInt(1, finalBalance);
                        preparedStatement.setInt(2, idAccountForTopUp);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        System.out.println("account topped up successfully");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public void withdrawFromAccount(Connection connection, Transaction transaction, int idAccountForWithdraw) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Accounts");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Accounts SET balance=? WHERE accountId=?");
            while (resultSet.next()) {
                if (resultSet.getInt("accountId") == idAccountForWithdraw) {
                    int finalBalance = resultSet.getInt("balance") - transaction.getAmount();
                    if (checkBalanceForExcess(finalBalance)) {
                        preparedStatement.setInt(1, finalBalance);
                    }
                }
            }
            preparedStatement.setInt(2, idAccountForWithdraw);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("withdrawal was successful");
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public void deleteAccount(Connection connection, int idAccountForDelete) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Accounts WHERE accountId=?");
            preparedStatement.setInt(1, idAccountForDelete);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("account deleted successfully!");
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public void deleteUser(Connection connection, int idUserForDelete) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Users WHERE userId=?");
            preparedStatement.setInt(1, idUserForDelete);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("user deleted successfully!");
        } catch (SQLException e) {
            System.out.println("SQL was not found");
        }
    }

    public boolean isCurrencyExists(Connection connection, int idUser, Account account) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Accounts");
            while (resultSet.next()) {
                if (resultSet.getInt("userId") == idUser) {
                    if (resultSet.getString("currency").equals(account.getCurrency())) {
                        return false;
                    }
                }
            }
            statement.close();
            resultSet.close();
        } catch (
                SQLException e) {
            System.out.println("SQL was not found");
        }
        return true;
    }

    public boolean isUserExists(Connection connection, int idUser) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Users");
            while (resultSet.next()) {
                if (resultSet.getInt("userId") == idUser) {
                    return true;
                }
            }
            statement.close();
            resultSet.close();
        } catch (
                SQLException e) {
            System.out.println("SQL was not found");
        }
        System.out.println("no such user exists!");
        return false;
    }

    public boolean isAccountExists(Connection connection, int idAccountForTopUp) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Accounts");
            while (resultSet.next()) {
                if (resultSet.getInt("accountId") == idAccountForTopUp) {
                    return true;
                }
            }
            statement.close();
            resultSet.close();
        } catch (
                SQLException e) {
            System.out.println("SQL was not found");
        }
        System.out.println("no such account exists!");
        return false;
    }

    public boolean checkBalanceForExcess(int finalBalance) {
        if (finalBalance <= 2000000000 & finalBalance > 0) {
            return true;
        } else {
            System.out.println("invalid balance");
            return false;
        }
    }
}

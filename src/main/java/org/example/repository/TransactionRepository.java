package org.example.repository;
import org.example.dto.Transaction;
import org.example.enums.TransactionType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    public boolean saveReFill(Long number, Double amount) {
        Connection connection = DBConnection.getConnection();
        String sql = "call  make_re_fill(%s,%s);";
        sql = String.format(sql,number,amount);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getTransactionListForUser(Integer id) {
        List<String> stringList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = """
                    select number, address, amount, tr.created_date, tr.type from card as c\s
                    join transactions as tr on c.number = tr.card_number\s
                    join terminal as t on t.code = tr.terminal_code where c.profile_id = ?
                    order by created_date desc""";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String string = "";
                string+=resultSet.getLong("number")+" ";
                string+= resultSet.getString("address")+" ";
                string+= resultSet.getDouble("amount")+" ";
                string+= resultSet.getTimestamp("created_date")+" ";
                string+= resultSet.getString("type")+" ";
               stringList.add(string);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionListForUser()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return stringList;
    }

    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from transactions order by created_date desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getLong("card_number"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionList()");
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> todayTransactionsList(LocalDate localDate) {
        List<Transaction> transactionList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from transactions where cast(created_date as date) = cast(? as date) order by created_date desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getLong("card_number"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionList()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return transactionList;
    }

    public List<Transaction> getIntermediateTransactions(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactionList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from transactions where cast(created_date as date) >= cast(? as date) " +
                                                    "and cast(created_date as date) < cast(? as date)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate.atStartOfDay()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getLong("card_number"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionList()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return transactionList;
    }

    public List<Transaction> getTransactionListByTerminal(String code) {
        List<Transaction> transactionList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from transactions where terminal_code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getLong("card_number"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionListByTerminal()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return transactionList;
    }

    public List<Transaction> getTransactionListByCard(Long number) {
        List<Transaction> transactionList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from transactions where card_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCard_number(resultSet.getLong("card_number"));
                transaction.setTerminal_code(resultSet.getString("terminal_code"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getTransactionListByTerminal()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return transactionList;
    }

    public boolean makePayment(Long number, String code, double amount, Long attoNumber) {
        Connection connection = DBConnection.getConnection();
        String sql = "call make_transaction(%s,'%s',%s,%s)";
        sql = String.format(sql, number,code,amount,attoNumber);
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1,number);
//            preparedStatement.setDouble(2,amount);
//            preparedStatement.setString(3,code);
//            preparedStatement.setDouble(4,attoNumber);
//            preparedStatement.executeUpdate(sql);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

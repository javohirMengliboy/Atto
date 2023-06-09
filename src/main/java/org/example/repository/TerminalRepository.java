package org.example.repository;

import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.enums.TerminalStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TerminalRepository {

    public boolean createTerminal(Terminal terminal){
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "insert into terminal(code,address,status,created_date) " +
                    "values(?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, terminal.getCode());
            preparedStatement.setString(2, terminal.getAddress());
            preparedStatement.setString(3, terminal.getStatus().toString());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Terminal getTerminal(String code){
        Terminal terminal = new Terminal();

        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from terminal where code = '%s'";
            sql = String.format(sql, code);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            terminal.setCode(resultSet.getString("code"));
            terminal.setAddress(resultSet.getString("address"));
            terminal.setStatus(TerminalStatus.valueOf(resultSet.getString("status")));
            terminal.setCreate_date(resultSet.getTimestamp("created_date").toLocalDateTime());
            connection.close();
            return terminal;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Terminal> getTerminalList(){
        List<Terminal> terminalList = new ArrayList<>();

        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from terminal";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Terminal terminal = new Terminal();
                terminal.setCode(resultSet.getString("code"));
                terminal.setAddress(resultSet.getString("address"));
                terminal.setStatus(TerminalStatus.valueOf(resultSet.getString("status")));
                terminal.setCreate_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                terminalList.add(terminal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return terminalList;
    }

    public boolean updateTerminal(String code, String address) {
        Connection connection = DBConnection.getConnection();
        String sql = "update terminal set address = ? where code = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2,code);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean changeTerminalStatus(String code, TerminalStatus status) {
        Connection connection = DBConnection.getConnection();
        String sql = "update terminal set status = ? where code = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status.toString());
            preparedStatement.setString(2,code);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean deleteTerminal(String code) {
        Connection connection = DBConnection.getConnection();
        String sql = "delete from card where code = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,code);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
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

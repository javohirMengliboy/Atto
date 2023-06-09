package org.example.repository;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.CardStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public boolean createAttoCard(Card card){
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "insert into card(number, exp_date, balance, status, phone, profile_id) " +
                    "values(?,?,?,?,?,?);";
            System.out.println(ComponentContainer.profile.toString());
            System.out.println(card.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, card.getNumber());
            preparedStatement.setString(2, card.getExp_date());
            preparedStatement.setDouble(3, card.getBalance());
            preparedStatement.setString(4, card.getStatus().toString());
            preparedStatement.setString(5, ComponentContainer.profile.getPhone());
            preparedStatement.setInt(6, ComponentContainer.profile.getId());
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

    public boolean createCard(Card card){
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "insert into card(number, exp_date, balance, status) " +
                    "values(?,?,?,?);";
            System.out.println(ComponentContainer.profile.toString());
            System.out.println(card.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, card.getNumber());
            preparedStatement.setString(2, card.getExp_date());
            preparedStatement.setDouble(3, card.getBalance());
            preparedStatement.setString(4, card.getStatus().toString());
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

    public Card getCardByNumber(Long number) {
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from card where number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            Card card = new Card();
            resultSet.next();
            card.setNumber(resultSet.getLong("number"));
            card.setExp_date(resultSet.getString("exp_date"));
            card.setBalance(resultSet.getDouble("balance"));
            card.setPhone(resultSet.getString("phone"));
            card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
            return card;
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Card> getCardList(Profile profile) {
        List<Card> cardList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from card where profile_id = ? and status != 'INVISIBLE'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,profile.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Card card = new Card();
                card.setNumber(resultSet.getLong("number"));
                card.setExp_date(resultSet.getString("exp_date"));
                card.setBalance(resultSet.getDouble("balance"));
                card.setPhone(resultSet.getString("phone"));
                card.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                cardList.add(card);
            }
        } catch (SQLException e) {
            System.out.println("What's wrong in getCardList()");
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return cardList;
    }

    public boolean changeCardStatus(Long number, CardStatus status){
        Connection connection = DBConnection.getConnection();
        String sql = "update card set status = ? where number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,status.toString());
            preparedStatement.setLong(2, number);
            preparedStatement.executeUpdate();
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

    public List<Card> getAllCardList(){
        List<Card> cardList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from card";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Card card = new Card();
                card.setNumber(resultSet.getLong("number"));
                card.setExp_date(resultSet.getString("exp_date"));
                card.setBalance(resultSet.getDouble("balance"));
                card.setPhone(resultSet.getString("phone"));
                card.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                cardList.add(card);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("What's wrong getAllCardList()");
            e.printStackTrace();
        }
        return cardList;
    }

    public boolean cardUpdate(Long number, String exp_date){
        Connection connection = DBConnection.getConnection();
        String sql = "update card set exp_date = ? where number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, exp_date);
            preparedStatement.setLong(2,number);
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

    public boolean deleteCard(Long number) {
        Connection connection = DBConnection.getConnection();
        String sql = "delete from card where number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,number);
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

    public int addCard(Card card, Long number) {
        Connection connection = DBConnection.getConnection();
        String sql = "update card set profile_id = ?," +
                                     "phone = ?," +
                                     "status = ?," +
                                     "created_date = ? where number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, card.getProfile_id());
            preparedStatement.setString(2, card.getPhone());
            preparedStatement.setString(3, card.getStatus().toString());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(card.getCreated_date()));
            preparedStatement.setLong(5,number);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean deleteCardByUser(Long number) {
        Connection connection = DBConnection.getConnection();
        String sql = "update card set status = 'INVISIBLE' where number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,number);
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

//    public boolean reFill(Long number, Double amount) {
//        Connection connection = DBConnection.getConnection();
//        String sql = "update card set balance = ? where number = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setDouble(1,amount);
//            preparedStatement.setLong(2,number);
//            preparedStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            return false;
//        }finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public Card getAttoCard() {
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from card where status = 'ATTO'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Card card = new Card();
            resultSet.next();
            card.setId(resultSet.getInt("id"));
            card.setNumber(resultSet.getLong("number"));
            card.setExp_date(resultSet.getString("exp_date"));
            card.setBalance(resultSet.getDouble("balance"));
            card.setPhone(resultSet.getString("phone"));
            card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
            return card;
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public Profile getProfile() {
//        return profile;
//    }
//
//    public void setProfile(Profile profile) {
//        this.profile = profile;
//    }
}

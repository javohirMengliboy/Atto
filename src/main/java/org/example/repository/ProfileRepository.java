package org.example.repository;


import org.example.dto.Profile;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfileRepository {
    public boolean addProfile(Profile profile){
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "insert into profile(name, surname, phone, login, password, created_date, status, role) " +
                    "values(?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSurname());
            preparedStatement.setString(3, profile.getPhone());
            preparedStatement.setString(4, profile.getLogin());
            preparedStatement.setString(5, profile.getPassword());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, profile.getStatus().toString());
            preparedStatement.setString(8, profile.getRole().toString());
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



    public List<Profile> getProfileList() {
        List<Profile> profileList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from profile";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Profile profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setName(resultSet.getString("name"));
                profile.setSurname(resultSet.getString("surname"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setLogin(resultSet.getString("login"));
                profile.setPassword(resultSet.getString("password"));
                profile.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
                profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
                profileList.add(profile);
            }
        } catch (SQLException e) {
            System.out.println("whats wrong in getProfileList()");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return profileList;
    }

    public Profile getProfileByPhone(String phone) {
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from profile where phone = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Profile profile = new Profile();
            profile.setId(resultSet.getInt("id"));
            profile.setName(resultSet.getString("name"));
            profile.setSurname(resultSet.getString("surname"));
            profile.setPhone(resultSet.getString("phone"));
            profile.setLogin(resultSet.getString("login"));
            profile.setPassword(resultSet.getString("password"));
            profile.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
            profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
            profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
            return profile;
        } catch (SQLException e) {
            return null;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Profile getProfileByLogin(String login) {
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from profile where login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Profile profile = new Profile();
            profile.setId(resultSet.getInt("id"));
            profile.setName(resultSet.getString("name"));
            profile.setSurname(resultSet.getString("surname"));
            profile.setPhone(resultSet.getString("phone"));
            profile.setLogin(resultSet.getString("login"));
            profile.setPassword(resultSet.getString("password"));
            profile.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
            profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
            profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
            return profile;
        } catch (SQLException e) {
            return null;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean changeProfileStatus(String phone, ProfileStatus status) {
        Connection connection = DBConnection.getConnection();
        String sql = "update card set status = ? where phone = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,status.toString());
            preparedStatement.setString(2, phone);
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

    public Profile getAdmin() {
        Connection connection = DBConnection.getConnection();
        try {
            String sql = "select * from profile where role = 'ADMIN'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Profile profile = new Profile();
            profile.setId(resultSet.getInt("id"));
            profile.setName(resultSet.getString("name"));
            profile.setSurname(resultSet.getString("surname"));
            profile.setPhone(resultSet.getString("phone"));
            profile.setLogin(resultSet.getString("login"));
            profile.setPassword(resultSet.getString("password"));
            profile.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
            profile.setStatus(ProfileStatus.valueOf(resultSet.getString("status")));
            profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
            return profile;
        } catch (SQLException e) {
            return null;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

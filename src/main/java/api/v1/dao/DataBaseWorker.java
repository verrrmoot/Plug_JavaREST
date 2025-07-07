package api.v1.dao;

import api.v1.exceptions.SelectException;
import api.v1.models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataBaseWorker {
    private static final String URL = "jdbc:postgresql://192.168.0.16:5432/postgres_plug";
    private static final String USER = "verrrmoot";
    private static final String PASSWORD = "verrrmoot";

    public User select(String login) throws SelectException {
        String query = "SELECT * FROM users_auth u JOIN users_emails e " +
                        "ON u.login = e.login WHERE u.login = '" + login + "'";
        User user = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstatement = connection.prepareStatement(query);
             ResultSet resultSet = pstatement.executeQuery())
            {
                if (resultSet.next()){
                    user = new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(resultSet.getTimestamp("date").getTime()))
                );
                } else if (user == null) {
                    throw new SelectException("User " + login + " not found");
                }
                return user;
            }
        catch (SQLException e){
            throw new SelectException("Error with select " + e.getMessage());
        }
    }

    public int insert(User user) throws SelectException {
        String sqlquery = "INSERT INTO users_auth VALUES (?, ?, ?); \n" +
                "INSERT INTO users_emails VALUES (?, ?)";
        int inserts = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstatement = connection.prepareStatement(sqlquery)){

            pstatement.setString(1, user.getLogin());
            pstatement.setString(2, user.getPassword());
            pstatement.setTimestamp(3, Timestamp.valueOf(user.getDate()));

            pstatement.setString(4, user.getEmail());
            pstatement.setString(5, user.getLogin());
            inserts += pstatement.executeUpdate();
            return inserts;
        }
        catch (SQLException e){
            throw new SelectException("Error with insert " + e.getMessage());
        }
    }

}

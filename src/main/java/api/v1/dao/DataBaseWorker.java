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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users_auth u JOIN users_emails e " +
                    "ON u.login = e.login " +
                    "WHERE u.login = '" + login + "'");
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
        }
        catch (SQLException e){
            System.out.println("Error with connection " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();
            }
            catch (SQLException e){
                System.out.println("error with close connection" + e.getMessage());
            }
        }
        return user;
    }

    public int insert(User user) throws SelectException {
        String sqlauth = "INSERT INTO users_auth VALUES (?, ?, ?)";
        String sqlemail = "INSERT INTO users_emails VALUES (?, ?)";
        int inserts = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            connection.setAutoCommit(false);

            try (PreparedStatement psauth = connection.prepareStatement(sqlauth);
                PreparedStatement psemail = connection.prepareStatement(sqlemail)){

                psauth.setString(1, user.getLogin());
                psauth.setString(2, user.getPassword());
                psauth.setTimestamp(3, Timestamp.valueOf(user.getDate()));
                inserts += psauth.executeUpdate();

                psemail.setString(1, user.getEmail());
                psemail.setString(2, user.getLogin());
                inserts += psemail.executeUpdate();

                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                System.out.println("Error with insert " + e.getMessage());
                inserts = 0;
                throw new SelectException("Error with insert " + e.getMessage());
            }
//            finally {
//                connection.setAutoCommit(true);
//            }
        }
        catch (SQLException e){
            System.out.println("Error connection " + e.getMessage());
        }
        return inserts;
    }

}

package modul;

import model.Role;
import model.User;

import java.net.UnknownServiceException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Connect to database*/
public class DBEngine {
    private Connection connection;


    public DBEngine() {
        connection = connect();
    }



    public boolean isConnected() {
        return (connection != null);
    }

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/blogDB" +
                "?useUnicode=yes&characterEncoding=UTF-8";

        Properties properties = new Properties();
        properties.put("user", System.getenv("DB_USER"));
        properties.put("password", System.getenv("DB_PASSWORD"));

        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> listAllUsers() {
        String query = "SELECT * FROM user";


        List<User> usersInDB = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                long userId = resultSet.getLong("id");
                String loginName = resultSet.getString("login_name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                Role role = Role.valueOf(resultSet.getString("role"));
                LocalDateTime regTime = resultSet.getTimestamp("reg_date").toLocalDateTime();
                boolean isSuspended = resultSet.getBoolean("is_suspended");

                User user = new User(userId, loginName, password, email, name, role, regTime, isSuspended);

                usersInDB.add(user);
            }

        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return usersInDB;
    }

}

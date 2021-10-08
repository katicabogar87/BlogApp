package module;

import model.Admin;
import model.Moderator;
import model.Role;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Queries related to User and derived Classes (Moderator, Admin)
 * */

public class UserController {

    private DBConnector dbConnector = new DBConnector();
    private BlogController blogController = new BlogController();

    public List<User> listAllRegisteredUsers() {
        String query = "SELECT * FROM user";
        List<User> usersInDB = new ArrayList<>();

        try {
            Statement statement = dbConnector.getConnection().createStatement();
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

                switch (role) {
                    case GUEST -> {
                    }
                    case REG_USER -> {
                        User user = new User(userId, loginName, password, email, name, role, regTime, isSuspended);
                        usersInDB.add(user);
                        user.setBlogs(blogController.findBlogsOfUser(user.getUserId()));
                    }
                    case MODERATOR -> {
                        Moderator moderator = new Moderator(userId, loginName, password, email, name, role, regTime, isSuspended);
                        usersInDB.add(moderator);
                        moderator.setBlogs(blogController.findBlogsOfUser(moderator.getUserId()));
                    }
                    case ADMIN -> {
                        Admin admin = new Admin(userId, loginName, password, email, name, role, regTime, isSuspended);
                        usersInDB.add(admin);
                        admin.setBlogs(blogController.findBlogsOfUser(admin.getUserId()));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return usersInDB;
    }

    public User findUserById(long id) {
        String query = "SELECT * FROM  user  WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                long userId = resultSet.getLong("id");
                String loginName = resultSet.getString("login_name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                Role role = Role.valueOf(resultSet.getString("role"));
                LocalDateTime regTime = resultSet.getTimestamp("reg_date").toLocalDateTime();
                boolean isSuspended = resultSet.getBoolean("is_suspended");

                switch (role) {
                    case GUEST -> {
                    }
                    case REG_USER -> {
                        User user = new User(userId, loginName, password, email, name, role, regTime, isSuspended);
                        user.setBlogs(blogController.findBlogsOfUser(user.getUserId()));
                        return user;
                    }
                    case MODERATOR -> {
                        Moderator moderator = new Moderator(userId, loginName, password, email, name, role, regTime, isSuspended);
                        moderator.setBlogs(blogController.findBlogsOfUser(moderator.getUserId()));
                        return moderator;
                    }
                    case ADMIN -> {
                        Admin admin = new Admin(userId, loginName, password, email, name, role, regTime, isSuspended);
                        admin.setBlogs(blogController.findBlogsOfUser(admin.getUserId()));
                        return admin;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("???");
            e.printStackTrace();
        }
        return null;
    }
}

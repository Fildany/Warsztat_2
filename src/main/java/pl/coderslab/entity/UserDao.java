package pl.coderslab.entity;


import pl.coderslab.workshop.BCrypt;
import pl.coderslab.workshop.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {

    public User[] findAll() {
        User[] users = new User[0];
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement("SELECT * FROM users");
            preStmt.executeQuery();
            ResultSet rs = preStmt.getResultSet();
            while (rs.next()) {
                User user = new User();
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("id"));
                users = addToArray(user, users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }


    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            preStmt.setInt(1, userId);
            preStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(
                    "UPDATE users SET username = ?, email = ?, password = ? Where id = ?");
            preStmt.setString(1, user.getUserName());
            preStmt.setString(2, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.setInt(4, user.getId());
            preStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User find(int id) {

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            preStmt.setInt(1, id);
            preStmt.executeQuery();
            ResultSet rs = preStmt.getResultSet();
            if (rs.next()) {
                User user = new User();
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("id"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement("INSERT INTO users(email, username, password) VALUES (?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preStmt.setString(2, user.getUserName());
            preStmt.setString(1, user.getEmail());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.execute();
            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}



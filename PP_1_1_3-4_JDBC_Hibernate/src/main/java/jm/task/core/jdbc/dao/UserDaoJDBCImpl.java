package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO users (name, lastName, age)
            VALUES (?, ?, ?)
            """;
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS users
            (
                id       BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
                name     VARCHAR(128) NOT NULL,
                lastName VARCHAR(128) NOT NULL,
                age      TINYINT      NOT NULL
            );
            """;
    private static final String DROP_TABLE = """
            DROP TABLE users
            """;
    private static final String SELECT_USERS = """
            SELECT id, name, lastName, age
            FROM users
            """;
    private static final String CLEAN_TABLE = """
            TRUNCATE TABLE users
            """;
    private UserDaoJDBCImpl() {

    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void dropUsersTable() {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (var connection = Util.open();
        var preparedStatement = connection.prepareStatement(SAVE_SQL);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte( 3, age);
            preparedStatement.executeUpdate();
            System.out.println("User c именем — " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public boolean removeUserById(long id) {
        try (var connection = Util.open();
        var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (var connection = Util.open();
             var statement = connection.createStatement();) {
            var executeResult = statement.executeQuery(SELECT_USERS);
            while (executeResult.next()) {
                User user = new User();
                user.setId(executeResult.getLong("id"));
                user.setName(executeResult.getString("name"));
                user.setLastName(executeResult.getString("lastName"));
                user.setAge(executeResult.getByte("age"));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        System.out.println(list);
        return list;
    }

    public void cleanUsersTable() {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
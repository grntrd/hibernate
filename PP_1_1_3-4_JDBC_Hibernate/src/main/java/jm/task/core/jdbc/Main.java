package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static jm.task.core.jdbc.util.Util.closeSessionFact;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ab", "Oba", (byte) 11);
        userService.saveUser("Pe", "Pega", (byte) 22);
        userService.saveUser("Two", "Header", (byte) 33);
        userService.saveUser("Zdravo", "Bravo", (byte) 44);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        closeSessionFact();
    }
}
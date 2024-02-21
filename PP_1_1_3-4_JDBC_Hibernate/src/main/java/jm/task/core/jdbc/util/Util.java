package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DRIVER;

public final class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/users_repository";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    private Util() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users_repository");
            properties.setProperty("hibernate.connection.username", "root");
            properties.setProperty("hibernate.connection.password", "root");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.current_session_context_class", "thread");

            sessionFactory = new Configuration().addProperties(properties).addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration();
//                Properties properties = new Properties();
//                properties.put(DRIVER, "com.mysql.cj.jdbc.Driver");
//                properties.put(Environment.URL, URL);
//                properties.put(Environment.USER, USER);
//                properties.put(Environment.PASS, PASSWORD);
//                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//                properties.put(Environment.SHOW_SQL, "true");
//                properties.put(Environment.FORMAT_SQL, "true");
//                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                properties.put(Environment.HBM2DDL_AUTO, "create");
//                configuration.setProperties(properties);
//                configuration.addAnnotatedClass(User.class);
//                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                        .applySettings(configuration.getProperties()).build();
//                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            } catch (HibernateException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return sessionFactory;
//    }

    public static void closeSessionFact() {
        getSessionFactory().close();
    }
}




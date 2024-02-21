package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String s = "CREATE TABLE IF NOT EXISTS users" + "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(128) NOT NULL, lastName VARCHAR(128) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            session.createSQLQuery(s).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            String s = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(s).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge((byte) age);
            session.save(user);
            System.out.println("User c именем — " + name + " добавлен в базу данных");
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean removeUserById(long id) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            var user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("From " + User.class.getSimpleName(), User.class).getResultList();
            for (User user : list) {
                System.out.println(user);
            }
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            String s = "TRUNCATE TABLE users";
            session.createSQLQuery(s).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String USERS_CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,\s
            name VARCHAR(64) NOT NULL,\s
            lastname VARCHAR(64) NOT NULL,\s
            age TINYINT UNSIGNED NOT NULL )""";
    private final String USERS_DROP_TABLE = "DROP TABLE IF EXISTS `users`";
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(USERS_CREATE_TABLE)
                    .addEntity(jm.task.core.jdbc.model.User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(USERS_DROP_TABLE)
                    .addEntity(jm.task.core.jdbc.model.User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(jm.task.core.jdbc.model.User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(jm.task.core.jdbc.model.User.class);
            Root<User> root = criteria.from(jm.task.core.jdbc.model.User.class);
            criteria.select(root);
            Query<User> query = session.createQuery(criteria);
            list = query.getResultList();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        List<User> list = getAllUsers();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            for (User user : list) {
                session.delete(user);
            }
            session.getTransaction().commit();
        }
    }
}

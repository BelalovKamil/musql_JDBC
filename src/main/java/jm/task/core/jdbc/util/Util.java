package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/dbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final SessionFactory sessionFactory;
    private Connection connection;

    static {
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.driver_class",
                        "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.connection.username", USERNAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.dialect",
                        "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.current_session_context_class",
                        "thread")
                .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                .buildSessionFactory();
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        return connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}

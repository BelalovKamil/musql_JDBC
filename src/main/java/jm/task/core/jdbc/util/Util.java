package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String URL = "jdbc:mysql://localhost:3306/dbtest";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private Connection connection;

    public Connection getConnection() throws SQLException {
//        if (connection != null) {
//            return connection;
//        }
        return connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

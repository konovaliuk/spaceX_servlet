package me.braun.spacex.connection;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass

public class ConnectionPool {

    private final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("dao");
    private final String URL = RESOURCE_BUNDLE.getString("db.url");
    private final String USERNAME = RESOURCE_BUNDLE.getString("db.login");
    private final String PASSWORD = RESOURCE_BUNDLE.getString("db.password");
    private final List<Connection> connectionPool = new LinkedList<>();
    private final List<Connection> usedConnections = new LinkedList<>();


    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    private Connection createConnection(String url, String username, String password)
            throws SQLException {
        log.info(url);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);

    }

    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {

            connectionPool.add(createConnection(URL, USERNAME, PASSWORD));
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(ConnectionPool::releaseConnection);
        for (Connection connection : connectionPool) {
            connection.close();
        }
        connectionPool.clear();
    }
}



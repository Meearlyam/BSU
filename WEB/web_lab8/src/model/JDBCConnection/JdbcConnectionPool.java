package model.JDBCConnection;

import model.exception.JDBCConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * JDBCConnector class
 * @author Vera Shavela
 * @version 1.0.0
 */
public class JdbcConnectionPool {

    private static final String DB_PROPERTIES = "database/database.properties";
    private static JdbcConnectionPool instance;
    private static Logger logger = LogManager.getLogger("database_layer");
    private final int initConnectionCount = 5;
    private String driver;
    private String DB_URL;
    private String user;
    private String password;
    private BlockingQueue<Connection> connections;

    /**
     * init database connection from properties file
     *
     * @throws JDBCConnectionException if properties file loading error
     */
    public JdbcConnectionPool() throws JDBCConnectionException {
        if(instance != null) {
            return;
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(DB_PROPERTIES));
            logger.info("Properties file loaded");
        } catch (IOException e) {
            throw new JDBCConnectionException("properties file not loaded");
        }
        driver = properties.getProperty("driver");
        DB_URL = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");


        try {
            Class.forName(driver);
            logger.info("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new JDBCConnectionException("Error loading driver!", e);
        }

        connections = new ArrayBlockingQueue<>(initConnectionCount);
        try {
            for(int i = 0; i < initConnectionCount; ++i) {
                Connection connection = DriverManager.getConnection(DB_URL, user, password);
                if(connection == null) {
                    throw new JDBCConnectionException("URL " + DB_URL + " contains incorrect driver type.");
                }
                connections.add(connection);
                logger.info("Connection " + i + " was established.");
            }
        } catch (SQLException e) {
            throw new JDBCConnectionException("Failed to establish connection", e);
        }
        logger.info("JDBC pool of connections initiated.");
    }

    /**
     * return instance  JdbcConnectionPool or create it
     *
     * @return instance of Singleton
     */
    public static synchronized JdbcConnectionPool getInstance() throws JDBCConnectionException {
        if(instance == null) {
            instance = new JdbcConnectionPool();
        }
        return instance;
    }

    /**
     * deinit database connections pool
     *
     * @throws JDBCConnectionException if properties file loading error
     */
    public synchronized void deinitJdbcConnector() throws JDBCConnectionException {
        try {
            while(connections.size() > 0) {
                connections.take().close();
            }
        } catch(SQLException e) {
            throw new JDBCConnectionException("Could not close database connection", e);
        } catch(InterruptedException e) {
            throw new JDBCConnectionException("Problem with concurrent queue", e);
        }
        logger.info("DB connections pool deinited");
    }

    /**
     * take connection from pool
     *
     * @return connection
     * @throws JDBCConnectionException if something goes wrong
     */
    public synchronized Connection getConnection() throws JDBCConnectionException {
        try {
            logger.info("got connection from the pool");
            return connections.take();
        } catch(InterruptedException e) {
            throw new JDBCConnectionException("Failed to get connection from pool", e);
        }
    }

    /**
     * return the connection to pool
     *
     * @param connection to add back to pool
     */
    public synchronized void releaseConnection(Connection connection) throws JDBCConnectionException {
        try {
            if(connection.isClosed()) {
                logger.info("connection was closed");
                logger.info("created new connection");
                Connection newConnection = DriverManager.getConnection(DB_URL, user, password);
                connections.add(newConnection);
            } else {
                connections.add(connection);
                logger.info("returned  connection to the pool");
            }
        } catch(SQLException e) {
            throw new JDBCConnectionException("Failed to establish connection", e);
        }

    }
}

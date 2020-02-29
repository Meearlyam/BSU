package model.dao;

import model.JDBCConnection.JdbcConnectionPool;
import model.exception.DAOException;
import model.exception.JDBCConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * dao abstract class
 * @author Vera Shavela
 * @version 1.0.0
 */
abstract class DAO {

    private JdbcConnectionPool dbcp;

    protected Logger logger = LogManager.getLogger("dao_layer");

    protected JdbcConnectionPool getJdbcConnector() {
        logger.info("requested to db connector");
        return dbcp;
    }

    /**
     * constructor
     *
     * @throws DAOException if can't create connection
     */
    protected DAO() throws DAOException {
        try {
            dbcp = new JdbcConnectionPool().getInstance();
            logger.info("Connection to database from dao initiated");
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can't create JDBCConnection ", e);
        }
    }
}

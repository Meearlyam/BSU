package model.dao;

import model.entity.Client;
import model.exception.DAOException;
import model.exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO {

    private static final String INSERT_CLIENT_SQL = "insert into clients (id, full_name, paid_orders_amount, personal_discount) values(?, ?, ?, ?)";

    private static final String DELETE_CLIENT_SQL = "delete from clients where id = ?";

    private static final String SELECT_ALL_CLIENTS_SQL = "select * from clients";

    private static final String SELECT_CLIENT_BY_ID_SQL = "select * from clients where id = ?";

    private static final String UPDATE_CLIENTS_DISCOUNT = "update clients set personal_discount = ? where paid_orders_amount > ?";

    /**
     * constructor
     *
     * @throws DAOException if Can't create connection
     */
    public ClientDAO() throws DAOException {
        super();
    }

    /**
     * read clients
     *
     * @return clients
     * @throws DAOException if can't execute query or have problems with connection
     */
    public List<Client> readAllClients() throws DAOException {
        List<Client> clients = new ArrayList<Client>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_CLIENTS_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int paidOrdersAmount = rs.getInt(2);
                int personalDiscount = rs.getInt(3);
                String fullName = rs.getString(4);
                Client client = new Client(id, fullName, paidOrdersAmount, personalDiscount);
                clients.add(client);
            }
            logger.info("read clients");
        } catch (SQLException e) {
            throw new DAOException("Read Clients exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBCConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
        return clients;
    }

    /**
     * update client personal discount
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void updateClientsDiscount(int discount, int N) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_CLIENTS_DISCOUNT);
            stmt.setInt(1, discount);
            stmt.setInt(2, N);
            stmt.execute();
            logger.info("updated clients discount");
        } catch (SQLException e) {
            throw new DAOException("Update Clients exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBCConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
    }

    /**
     * read client by id
     *
     * @return client
     * @throws DAOException if can't execute query or have problems with connection
     */
    public Client readClientById(int id) throws DAOException {
        Client client = null;
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_CLIENT_BY_ID_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int paidOrdersAmount = rs.getInt(2);
                int personalDiscount = rs.getInt(3);
                String fullName = rs.getString(4);
                client = new Client(id, fullName, paidOrdersAmount, personalDiscount);
            }
            logger.info("read client by id");
        } catch (SQLException e) {
            throw new DAOException("Read Client by id exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBCConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
        return client;
    }

    /**
     * insert client
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void insertClient(Client client) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_CLIENT_SQL);
            stmt.setInt(1, client.getId());
            stmt.setInt(2, client.getPaidOrdersAmount());
            stmt.setInt(3, client.getPersonalDiscount());
            stmt.setString(4, client.getFullName());
            stmt.execute();
            logger.info("inserted client");
        } catch (SQLException e) {
            throw new DAOException("Insert Client exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBCConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
    }

    /**
     * delete client
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void deleteClient(Client client) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_CLIENT_SQL);
            stmt.setInt(1, client.getId());
            stmt.execute();
            logger.info("deleted client");
        } catch (SQLException e) {
            throw new DAOException("Delete Client exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBCConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
    }
}

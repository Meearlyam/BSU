package model.dao;

import model.entity.Client;
import model.entity.Order;
import model.entity.Tour;
import model.exception.DAOException;
import model.exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO extends DAO {

    private static final String INSERT_ORDER_SQL = "insert into orders (id, tour_id, client_id) values(?, ?, ?)";

    private static final String DELETE_ORDER_SQL = "delete from orders where id = ?";

    private static final String SELECT_ALL_ORDERS_SQL = "select * from orders";

    private static final String SELECT_ORDER_BY_ID_SQL = "select * from orders where id = ?";

    private static final String GET_LAST_ORDER_ID_SQL = "select max(id) from orders";

    private static final String SELECT_ORDER_BY_CLIENT_ID_SQL = "select c.id, c.full_name, c.paid_orders_amount, c.personal_discount, t.id, t.type_id, t.name, t.cost, t.location, t.is_burning from clients c join orders o on c.id = o.client_id join tours t on o.tour_id = t.id where o.client_id = ?";

    /**
     * constructor
     *
     * @throws DAOException if can't create connection
     */
    public OrderDAO() throws DAOException {
        super();
    }

    /**
     * read orders
     *
     * @return orders
     * @throws DAOException if can't execute query or have problems with connection
     */
    public List<Order> readAllOrders() throws DAOException {
        List<Order> tours = new ArrayList<Order>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_ORDERS_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int tourId = rs.getInt(2);
                int clientId = rs.getInt(3);
                Order order = new Order(id, tourId, clientId);
                tours.add(order);
            }
            logger.info("read orders");
        } catch (SQLException e) {
            throw new DAOException("Read Orders exception ", e);
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
        return tours;
    }

    /**
     * read order by id
     *
     * @return order
     * @throws DAOException if can't execute query or have problems with connection
     */
    public Order readOrderById(int id) throws DAOException {
        Order order = null;
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ORDER_BY_ID_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int tourId = rs.getInt(2);
                int clientId = rs.getInt(3);
                order = new Order(id, tourId, clientId);
            }
            logger.info("read order by id");
        } catch (SQLException e) {
            throw new DAOException("Read Order by id exception ", e);
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
        return order;
    }

    /**
     * insert order
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void insertOrder(Order order) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(GET_LAST_ORDER_ID_SQL);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt(1) + 1;
            stmt = conn.prepareStatement(INSERT_ORDER_SQL);
            stmt.setInt(1, id);
            stmt.setInt(2, order.getTourId());
            stmt.setInt(3, order.getClientId());
            stmt.execute();
            logger.info("inserted order");
        } catch (SQLException e) {
            throw new DAOException("Insert Order exception ", e);
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
     * delete order
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void deleteOrder(Order order) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_ORDER_SQL);
            stmt.setInt(1, order.getId());
            stmt.execute();
            logger.info("deleted order");
        } catch (SQLException e) {
            throw new DAOException("Delete Order exception ", e);
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
     * get orders by client id
     *
     * @return list of orders
     * @throws DAOException if can't execute query or have problems with connection
     */
    public List<Map.Entry<Client, Tour>> readOrdersByClientId(int clientId) throws DAOException {
        List<Map.Entry<Client, Tour>> clientOrders = new ArrayList<Map.Entry<Client, Tour>>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ORDER_BY_CLIENT_ID_SQL);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString(2);
                int paidOrdersAmount = rs.getInt(3);
                int personalDiscount = rs.getInt(4);
                Client client = new Client(clientId, fullName, paidOrdersAmount, personalDiscount);

                int tourId = rs.getInt(5);
                int tourTypeId = rs.getInt(6);
                String tourName = rs.getString(7);
                double tourCost = rs.getDouble(8);
                String tourLocation = rs.getString(9);
                boolean isTourBurning = rs.getBoolean(10);
                Tour tour = new Tour(tourId, tourTypeId, tourName, tourCost, tourLocation, isTourBurning);

                clientOrders.add(new AbstractMap.SimpleImmutableEntry<>(client, tour));
            }
            logger.info("get orders by client id");
        } catch (SQLException e) {
            throw new DAOException("Read orders by client id exception ", e);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Failed to get connection from JDBConnector ", e);
        } finally {
            if (conn != null) {
                try {
                    getJdbcConnector().releaseConnection(conn);
                } catch (JDBCConnectionException e) {
                    throw new DAOException("Failed to return connection to db connector ", e);
                }
            }
        }
        return clientOrders;
    }

}

package model.dao;

import model.entity.Tour;
import model.entity.TourType;
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

public class TourDAO extends DAO {

    private static final String SELECT_ALL_TOURS_SQL = "select t.id, t.type_id, t.name, t.cost, t.location, t.is_burning, tt.id, tt.name from tours t join tour_types tt on t.type_id = tt.id";

    private static final String INSERT_TOUR_SQL = "insert into tours (id, type_id, name, cost, location, is_burning) values(?, ?, ?, ?, ?, ?)";

    private static final String DELETE_TOUR_SQL = "delete from tours where id = ?";

    private static final String SELECT_TOUR_BY_ID_SQL = "select * from tours where id = ?";

    private static final String SELECT_BURNING_TOURS_BY_TOUR_TYPE_ID_SQL = "select t.id, t.type_id, t.name, t.cost, t.location, t.is_burning, tt.id, tt.name from tours t join tour_types tt on t.type_id = tt.id where t.type_id = ? and t.is_burning = true";

    /**
     * constructor
     *
     * @throws DAOException if Can't create connection
     */
    public TourDAO() throws DAOException {
        super();
    }


    /**
     * read all tours
     *
     * @return list of tours
     * @throws DAOException if can't execute query or have problems with connection
     */
    public List<Map.Entry<Tour, TourType>> readAllTours() throws DAOException {
        List<Map.Entry<Tour, TourType>> toursWithTourType = new ArrayList<Map.Entry<Tour, TourType>>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TOURS_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int typeId = rs.getInt(2);
                String name = rs.getString(3);
                double cost = rs.getDouble(4);
                String location = rs.getString(5);
                boolean isBurning = rs.getBoolean(6);
                Tour tour = new Tour(id, typeId, name, cost, location, isBurning);

                int typeIdType = rs.getInt(7);
                String typeName = rs.getString(8);
                TourType tourType = new TourType(typeIdType, typeName);

                toursWithTourType.add(new AbstractMap.SimpleImmutableEntry<>(tour, tourType));
            }
            logger.info("read tours");
        } catch (SQLException e) {
            throw new DAOException("Select Tours exception ", e);
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
        return toursWithTourType;
    }

    /**
     * read tour by id
     *
     * @return tour
     * @throws DAOException if can't execute query or have problems with connection
     */
    public Tour readTourById(int id) throws DAOException {
        Tour tour = null;
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_TOUR_BY_ID_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int typeId = rs.getInt(2);
                String name = rs.getString(3);
                double cost = rs.getDouble(4);
                String location = rs.getString(5);
                boolean isBurning = rs.getBoolean(6);
                tour = new Tour(id, typeId, name, cost, location, isBurning);
            }
            logger.info("read tour by id");
        } catch (SQLException e) {
            throw new DAOException("Read Tour by id exception ", e);
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
        return tour;
    }

    /**
     * insert tour
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void insertTour(Tour tour) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_TOUR_SQL);
            stmt.setInt(1, tour.getId());
            stmt.setInt(2, tour.getTypeId());
            stmt.setString(3, tour.getName());
            stmt.setDouble(4, tour.getCost());
            stmt.setString(5, tour.getLocation());
            stmt.setBoolean(6, tour.getIsBurning());
            stmt.execute();
            logger.info("inserted tour");
        } catch (SQLException e) {
            throw new DAOException("Insert Tour exception ", e);
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
     * delete tour
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public void deleteTour(Tour tour) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_TOUR_SQL);
            stmt.setInt(1, tour.getId());
            stmt.execute();
            logger.info("deleted tour");
        } catch (SQLException e) {
            throw new DAOException("Delete Tour exception ", e);
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
     * read burning tours by tour type id
     *
     * @throws DAOException if can't execute query or have problems with connection
     */
    public List<Map.Entry<Tour, TourType>> readBurningToursByTourTypeId(int tourTypeId) throws DAOException {
        List<Map.Entry<Tour, TourType>> burningTours = new ArrayList<Map.Entry<Tour, TourType>>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_BURNING_TOURS_BY_TOUR_TYPE_ID_SQL);
            stmt.setInt(1, tourTypeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int typeId = rs.getInt(2);
                String name = rs.getString(3);
                double cost = rs.getDouble(4);
                String location = rs.getString(5);
                boolean isBurning = rs.getBoolean(6);
                Tour tour = new Tour(id, typeId, name, cost, location, isBurning);

                int typeIdType = rs.getInt(7);
                String typeName = rs.getString(8);
                TourType tourType = new TourType(typeIdType, typeName);

                burningTours.add(new AbstractMap.SimpleImmutableEntry<>(tour, tourType));
            }
            logger.info("read burning tours of specified type");
        } catch (SQLException e) {
            throw new DAOException("Read Burning Tours of specified type exception ", e);
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
        return burningTours;
    }
}

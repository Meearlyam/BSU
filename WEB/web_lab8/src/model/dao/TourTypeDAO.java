package model.dao;

import model.entity.TourType;
import model.exception.DAOException;
import model.exception.JDBCConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourTypeDAO extends DAO {
    private static final String INSERT_TOUR_TYPE_SQL = "insert into tour_types (id, name) values(?, ?)";

    private static final String DELETE_TOUR_TYPE_SQL = "delete from tout_types where id = ?";

    private static final String SELECT_ALL_TOUR_TYPES_SQL = "select * from tour_types";

    private static final String SELECT_TOUR_TYPE_BY_ID_SQL = "select * from tour_types where id = ?";

    /**
     * constructor
     *
     * @throws DAOException if Can't create connection
     */
    public TourTypeDAO() throws DAOException {
        super();
    }

    /**
     * read tour types
     *
     * @return tour types
     * @throws DAOException if Can't execute query or problems with connection
     */
    public List<TourType> readAllTourTypes() throws DAOException {
        List<TourType> tourTypes = new ArrayList<TourType>();
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TOUR_TYPES_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                TourType tourType = new TourType(id, name);
                tourTypes.add(tourType);
            }
            logger.info("read tour types");
        } catch (SQLException e) {
            throw new DAOException("Read Tour Types exception ", e);
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
        return tourTypes;
    }

    /**
     * read tour type by id
     *
     * @return tour type
     * @throws DAOException if Can't execute query or problems with connection
     */
    public TourType readTourTypeById(int id) throws DAOException {
        TourType tourType = null;
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_TOUR_TYPE_BY_ID_SQL);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                tourType = new TourType(id, name);
            }
            logger.info("read tour type by id");
        } catch (SQLException e) {
            throw new DAOException("Read Tour Type by id exception ", e);
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
        return tourType;
    }

    /**
     * insert tour type
     *
     * @throws DAOException if Can't execute query or problems with connection
     */
    public void insertTourType(TourType tourType) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_TOUR_TYPE_SQL);
            stmt.setInt(1, tourType.getId());
            stmt.setString(2, tourType.getName());
            stmt.execute();
            logger.info("inserted tour type");
        } catch (SQLException e) {
            throw new DAOException("Insert Tour Type exception ", e);
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
     * delete tour type
     *
     * @throws DAOException if Can't execute query or problems with connection
     */
    public void deleteTourType(TourType tourType) throws DAOException {
        Connection conn = null;
        try {
            conn = getJdbcConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_TOUR_TYPE_SQL);
            stmt.setInt(1, tourType.getId());
            stmt.execute();
            logger.info("deleted tour type");
        } catch (SQLException e) {
            throw new DAOException("Delete Tour Type exception ", e);
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

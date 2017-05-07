package edu.luc.cs439.system.facility.dal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

/**
 * Created by jlroo on 2/20/17.
 */

public class dbConnect {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://1localhost:5432/facilitydb";
    static final String USER = "admin";
    static final String PASS = "admin";
    static final Logger LOG = LoggerFactory.getLogger(dbConnect.class);

    @SuppressWarnings("unused")
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try { Class.forName(JDBC_DRIVER); }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement query = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        SetCommit(conn);
        SetIsolationLevel(conn, Connection.TRANSACTION_SERIALIZABLE);
        return conn;
    }

    private static void SetCommit(Connection connection) throws SQLException {
        try{
        connection.setAutoCommit(false);
        boolean mode = connection.getAutoCommit();
            if (mode) {
                RuntimeException error = new RuntimeException();
                error.getMessage();
                LOG.error(error.getMessage());
                throw error;
            }
        }catch (SQLException e){
            LOG.error(e.getMessage());
        }
    }

    private static void SetIsolationLevel(Connection connection, int level) throws SQLException {
        try {
            connection.setTransactionIsolation(level);
            int setLevel = connection.getTransactionIsolation();
            if (setLevel != level) {
                RuntimeException error = new RuntimeException();
                error.getMessage();
                LOG.error(error.getMessage());
                throw error;
            }
        }catch (SQLException e){
            LOG.error(e.getMessage());
        }
    }
}

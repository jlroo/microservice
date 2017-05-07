package edu.luc.cs439.system.facility.dal;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.contracts.models.facility.Details;
import edu.luc.cs439.system.contracts.models.facility.Facility;
import edu.luc.cs439.system.contracts.models.facility.FacilityImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jlroo on 2/20/17.
 */

public class FacilityDAO {

    public FacilityDAO() {}

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/facilitydb";
    static final String USER = "admin";
    static final String PASS = "admin";

    private static final String TableName           = "facility";
    private static final String IDColumn            = "facility_id";
    private static final String NameColumn          = "facility_name";
    private static final String roomNumberColumn    = "room_number";
    private static final String mediaColumn         = "media";
    private static final String capacityColumn      = "max_capacity";

    public Collection<Facility> ReadAllFacilities() throws Exception {
        List<Facility> FacilityList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //ChaosSource.ForDataAccess("Facility").ForMethod("GET").run();
            String sql_query = "SELECT * FROM " + TableName ;
            Statement statement = conn.createStatement();
            ResultSet response = statement.executeQuery(sql_query);
            while ( response.next() ) {

                int facilityID  = response.getInt(1);
                String name     = response.getString(2);
                int room        = response.getInt(3);
                boolean media   = response.getBoolean(4);
                int capacity    = response.getInt(5);

                Facility FacilityObj = new FacilityImpl();
                FacilityObj.setFacilityID(facilityID);
                FacilityObj.setName(name);
                FacilityObj.setRoomNumber(room);
                FacilityObj.setMedia(media);
                FacilityObj.setMaxCapacity(capacity);
                FacilityList.add(FacilityObj);
            }
            response.close();
            statement.close();
            conn.close();
        } finally{if (conn!=null) conn.close();}
        return FacilityList;
    }


    public void Insert(Facility newObj) throws Exception {
//        Connection conn = dbConnect.getConnection();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //ChaosSource.ForDataAccess("Facility").ForMethod("Insert").run();
            String sql = "INSERT INTO " + TableName +
                    " (facility_id,facility_name,room_number,media,max_capacity )" +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement sqlFacility = conn.prepareStatement(sql);
            sqlFacility.setInt(1, newObj.getFacilityID());
            sqlFacility.setString(2, newObj.getName());
            sqlFacility.setInt(3, newObj.getRoomNumber());
            sqlFacility.setBoolean(4, newObj.isMedia());
            sqlFacility.setInt(5, newObj.getMaxCapacity());
            sqlFacility.executeUpdate();
            sqlFacility.close();
            String query = "INSERT INTO facility_details VALUES (?,?,?,?,?)";
            PreparedStatement sqlDetails = conn.prepareStatement(query);
            Details facilityDetails = newObj.getDetails();

            if (facilityDetails.getPhoneNumber() == null) facilityDetails.setPhoneNumber("");
            if (facilityDetails.getDepartment() == null) facilityDetails.setDepartment("");

            sqlDetails.setInt(1,facilityDetails.getFacilityID() );
            sqlDetails.setString(2, facilityDetails.getPhoneNumber());
            sqlDetails.setString(3, facilityDetails.getDepartment());
            sqlDetails.setBoolean(4, facilityDetails.isOccupied());
            sqlDetails.setDate(5, Date.valueOf(facilityDetails.getInspected()));
            sqlDetails.executeUpdate();
            sqlDetails.close();
            conn.commit();
            conn.close();
        } finally{if (conn!=null) conn.close();}
    }

    public void Delete(int deleteID) throws Exception {
        //Connection conn = dbConnect.getConnection();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //ChaosSource.ForDataAccess("Facility").ForMethod("Delete").run();
            String sql = "DELETE FROM " + TableName + " WHERE " + IDColumn +  " = ?" ;
            PreparedStatement sqlDelete = conn.prepareStatement(sql);
            sqlDelete.setInt(1,deleteID);
            sqlDelete.executeUpdate();
            conn.commit();
            conn.close();
        } finally{if (conn!=null) conn.close();}
    }

    public void Update(int updateID,Facility updateObj) throws Exception {

        Connection conn = dbConnect.getConnection();
        try {
            //ChaosSource.ForDataAccess("Facility").ForMethod("Update").run();
            String sql = "UPDATE " + TableName + " SET " +
                    IDColumn            + " = ? , " +
                    NameColumn          + " = ? , " +
                    roomNumberColumn    + " = ? , " +
                    mediaColumn         + " = ? ," +
                    capacityColumn      + " = ? " +
                    " WHERE " + IDColumn + " = " + updateID;

            PreparedStatement sqlFacility = conn.prepareStatement(sql);
            sqlFacility.setInt(1, updateObj.getFacilityID());
            sqlFacility.setString(2, updateObj.getName());
            sqlFacility.setInt(3, updateObj.getRoomNumber());
            sqlFacility.setBoolean(4, updateObj.isMedia());
            sqlFacility.setInt(5, updateObj.getMaxCapacity());
            sqlFacility.executeUpdate();
            String query = "UPDATE " + "facility_details" +" SET " +
                            "facility_id = ? , "+
                            "phone_number = ? , "+
                            "department = ? , " +
                            "occupied = ? , "+
                            "inspection_date = ? " +
                        "WHERE " + IDColumn + " = " + updateID;

            PreparedStatement sqlDetails = conn.prepareStatement(query);
            Details facilityDetails = updateObj.getDetails();
            if (facilityDetails.getPhoneNumber() == null) facilityDetails.setPhoneNumber("");
            if (facilityDetails.getDepartment() == null) facilityDetails.setDepartment("");
            sqlDetails.setInt(1, facilityDetails.getFacilityID());
            sqlDetails.setString(2, facilityDetails.getPhoneNumber());
            sqlDetails.setString(3, facilityDetails.getDepartment());
            sqlDetails.setBoolean(4, facilityDetails.isOccupied());
            sqlDetails.setDate(5, Date.valueOf(facilityDetails.getInspected()));
            sqlDetails.executeUpdate();
            conn.commit();

        } finally{conn.close();}
    }

    public void cleanTables() throws Exception {
        Connection conn = dbConnect.getConnection();
        try {
            //ChaosSource.ForDataAccess("Facility").ForMethod("CleanTables").run();
            Statement response = conn.createStatement();
            String sql = String.format("DELETE FROM " + TableName + " CASCADE");
            response.executeUpdate(sql);
            conn.commit();
        } finally{conn.close();}
    }

}

package edu.luc.cs439.system.facility.dal;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.contracts.models.use.FacilityUse;
import edu.luc.cs439.system.contracts.models.use.FacilityUseImpl;
import edu.luc.cs439.system.contracts.models.use.InspectionImpl;
import edu.luc.cs439.system.contracts.models.use.Inspection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jlroo on 2/20/17.
 */


public class UseDAO {

    public UseDAO() {}

    private static final String TableName           = "facility_use";
    private static final String IDColumn            = "facility_id";
    private static final String customerColumn      = "customer_id";
    private static final String startColumn         = "reservation_start";
    private static final String endColumn           = "reservation_end";
    private static final String inspectionColumn    = "inspection_date";


    public Collection<FacilityUse> ReadAllOrders() throws Exception {
        Collection<FacilityUse> OrdersList = new ArrayList<>();
        Statement conn = dbConnect.getConnection().createStatement();
        try {
            ChaosSource.ForDataAccess("FacilityUse").ForMethod("GET").run();
            String query = "SELECT * FROM " + TableName ;
            ResultSet response = conn.executeQuery(query);
            FacilityUse useObj = new FacilityUseImpl();

            while ( response.next() ) {

                int orderNum          = response.getInt(1);
                int facilityID        = response.getInt(2);
                int customerID        = response.getInt(3);
                Date reservationStart = response.getDate(4);
                Date reservationEnd   = response.getDate(5);
                Date inspectionDate   = response.getDate(6);

                useObj.setOrderNumber(orderNum);
                useObj.setFacilityID(facilityID);
                useObj.setCustomerID(customerID);
                useObj.setReservationStart(reservationStart.toLocalDate());
                useObj.setReservationEnd(reservationEnd.toLocalDate());
                InspectionImpl inspectionObj = new InspectionImpl();
                inspectionObj.setInspectionDate(inspectionDate.toLocalDate());
                useObj.setInspection(inspectionObj);
                OrdersList.add(useObj);
            }
            response.close();
        }finally { conn.close(); }
        return OrdersList;
    }

    public void Insert(FacilityUse newObj) throws Exception {

        Connection conn = dbConnect.getConnection();
        try {
            ChaosSource.ForDataAccess("FacilityUse").ForMethod("Insert").run();
            String sql = "INSERT INTO facility_use (facility_id, customer_id, reservation_start, reservation_end, inspection_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement sqlFacilityUse = conn.prepareStatement(sql);
            sqlFacilityUse.setInt(1, newObj.getFacilityID());
            sqlFacilityUse.setInt(2, newObj.getCustomerID());
            sqlFacilityUse.setDate(3, Date.valueOf(newObj.getReservationStart()));
            sqlFacilityUse.setDate(4, Date.valueOf(newObj.getReservationEnd()));
            LocalDate inspectionDate = newObj.getInspection().getInspectionDate();
            sqlFacilityUse.setDate(5, Date.valueOf(inspectionDate));
            sqlFacilityUse.executeUpdate();
            conn.commit();

            String query = "INSERT INTO inspection VALUES (?,?,?,?,?)";

            PreparedStatement sqlInspection = conn.prepareStatement(sql);

            Inspection inspectionDetails = newObj.getInspection();
            sqlInspection.setInt(1, newObj.getFacilityID());
            sqlInspection.setString(2, inspectionDetails.getInspectionCode());
            sqlInspection.setDate(3, Date.valueOf(inspectionDetails.getInspectionDate()));
            sqlInspection.setBoolean(4, inspectionDetails.getPassedInspection());
            sqlInspection.setString(5, inspectionDetails.getDescription());
            sqlInspection.executeUpdate();
            conn.commit();
        } finally{
            conn.close();
        }
    }

    public void Delete(int deleteID) throws Exception {
        Connection conn = dbConnect.getConnection();
        try {
            ChaosSource.ForDataAccess("FacilityUse").ForMethod("Delete").run();
            String sql = "DELETE FROM " + TableName + " WHERE " + IDColumn + " = ?";
            PreparedStatement sqlDelete = conn.prepareStatement(sql);
            sqlDelete.setInt(1, deleteID);
            sqlDelete.executeUpdate();
            conn.commit();
        }finally{conn.close();}

    }

    public void Update(int updateID,FacilityUse updateObj) throws Exception {

        Connection conn = dbConnect.getConnection();

        try {
            ChaosSource.ForDataAccess("FacilityUse").ForMethod("Update").run();
            String sql = "UPDATE facility_use SET " +
                    "facility_id=?, customer_id=?, reservation_start=?, " +
                    "reservation_end=?, inspection_date=?" +
                    " WHERE "+ IDColumn + " = " + updateID;

            PreparedStatement sqlFacilityUse = conn.prepareStatement(sql);
            sqlFacilityUse.setInt(1, updateObj.getFacilityID());
            sqlFacilityUse.setInt(2, updateObj.getCustomerID());
            sqlFacilityUse.setDate(3, Date.valueOf(updateObj.getReservationStart()));
            sqlFacilityUse.setDate(4, Date.valueOf(updateObj.getReservationEnd()));
            sqlFacilityUse.setDate(5, Date.valueOf(updateObj.getInspection().getInspectionDate()));
            sqlFacilityUse.executeUpdate();
            conn.commit();

            String query = "UPDATE " + "inspection" +" SET " +
                    "inspection_code = ? , "+
                    "inspection_date = ? , " +
                    "passed_inspection = ? , "+
                    "description = ? " +
                    "WHERE " + IDColumn + " = " + updateID;

            PreparedStatement sqlInspection = conn.prepareStatement(query);
            Inspection inspectionDetails = updateObj.getInspection();
            sqlInspection.setString(1, inspectionDetails.getInspectionCode());
            sqlInspection.setDate(2, Date.valueOf(inspectionDetails.getInspectionDate()));
            sqlInspection.setBoolean(3, inspectionDetails.getPassedInspection());
            sqlInspection.setString(4, inspectionDetails.getDescription());
            sqlInspection.executeUpdate();
            conn.commit();

        } finally{conn.close();}
    }

}
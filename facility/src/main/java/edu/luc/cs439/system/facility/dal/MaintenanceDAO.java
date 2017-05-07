package edu.luc.cs439.system.facility.dal;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.contracts.models.maintenance.Maintenance;
import edu.luc.cs439.system.contracts.models.maintenance.MaintenanceImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jlroo on 2/20/17.
 */

public class MaintenanceDAO {

    public MaintenanceDAO() {}

    private static final String TableName       = "maintenance";
    private static final String IDColumn        = "facility_id";
    private static final String startColumn     = "start_work";
    private static final String endColumn       = "end_work";
    private static final String downtimeColumn  = "facility_downtime";
    private static final String costColumn      = "maintenance_cost";
    private static final String logColumn       = "maintenance_log";
    private static final String statusColumn    = "maintenance_status";


    public Collection<Maintenance> ReadAllMaintenance() throws Exception {
        List<Maintenance> maintenanceList = new ArrayList<>();
        Statement conn = dbConnect.getConnection().createStatement();
        try {
            ChaosSource.ForDataAccess("Maintenance").ForMethod("GET").run();
            String query = "SELECT * FROM " + TableName ;
            ResultSet response = conn.executeQuery(query);
            while ( response.next() ) {

                int facilityID          = response.getInt(1);
                Date start              = response.getDate(2);
                Date end                = response.getDate(3);
                Long downtime           = response.getLong(4);
                int maintenanceCost     = response.getInt(5);
                String maintenanceLog   = response.getString(6);
                String work_status      = response.getString(7);

                Maintenance maintenanceObj = new MaintenanceImpl();
                maintenanceObj.setFacilityID(facilityID);
                maintenanceObj.setMaintenanceStart(start.toLocalDate());
                maintenanceObj.setMaintenanceEnd(end.toLocalDate());
                maintenanceObj.setFacilityDowntime(downtime);
                maintenanceObj.setMaintenanceCost(maintenanceCost);
                maintenanceObj.setMaintenanceLog(maintenanceLog);
                maintenanceObj.setMaintenanceStatus(work_status);
                maintenanceList.add(maintenanceObj);
            }
            response.close();
            conn.close();
        }
        catch (SQLException se) {
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return maintenanceList;
    }

    public void Insert(Maintenance newObj) throws Exception {
        Connection conn = dbConnect.getConnection();
        try {
            ChaosSource.ForDataAccess("Maintenance").ForMethod("Insert").run();
            String sql = "INSERT INTO " + TableName + " VALUES (?,?,?,?,?,?,?)";
            PreparedStatement response = conn.prepareStatement(sql);
            response.setInt(1, newObj.getFacilityID());
            response.setDate(2, Date.valueOf(newObj.getMaintenanceStart()));
            response.setDate(3, Date.valueOf(newObj.getMaintenanceEnd()));
            response.setLong(4, newObj.getFacilityDowntime());
            response.setInt(5, newObj.getMaintenanceCost());
            response.setString(6, newObj.getMaintenanceLog());
            String status = "ON PROGRESS";
            if(newObj.getMaintenanceEnd().isBefore(LocalDate.now())) status="COMPLETED";
            response.setString(7,status);
            response.executeUpdate();
            conn.commit();
            conn.close();
        }catch (SQLException se) {
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void Delete(int deleteID) throws Exception {
        Connection conn = dbConnect.getConnection();
        try {
            ChaosSource.ForDataAccess("Maintenance").ForMethod("Delete").run();
            String sql = "DELETE FROM " + TableName + " WHERE " + IDColumn +  " = ?" ;
            PreparedStatement sqlDelete = conn.prepareStatement(sql);
            sqlDelete.setInt(1,deleteID);
            sqlDelete.executeUpdate();
            conn.commit();
            conn.close();
        }
        catch (SQLException se) {
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void Update(int updateID,Maintenance maintenanceObj) throws Exception {
        Connection conn = dbConnect.getConnection();
        try {
            ChaosSource.ForDataAccess("Maintenance").ForMethod("Update").run();
            String sql = "UPDATE " + TableName + " SET " +
                        IDColumn        + " = ? , " +
                        startColumn     + " = ? , " +
                        endColumn       + " = ? , " +
                        downtimeColumn  + " = ? , " +
                        costColumn      + " = ? , " +
                        logColumn       + " = ? , " +
                        statusColumn    + " = ? " +
                    " WHERE " + IDColumn + " = " + updateID;

            PreparedStatement response = conn.prepareStatement(sql);
            response.setInt(1, maintenanceObj.getFacilityID());
            response.setDate(2, Date.valueOf(maintenanceObj.getMaintenanceStart()));
            response.setDate(3, Date.valueOf(maintenanceObj.getMaintenanceEnd()));
            response.setLong(4,maintenanceObj.getFacilityDowntime());
            response.setInt(5,maintenanceObj.getMaintenanceCost());
            response.setString(6,maintenanceObj.getMaintenanceLog());
            String status = "ON PROGRESS";
            if(maintenanceObj.getMaintenanceEnd().isBefore(LocalDate.now())) status="COMPLETED";
            response.setString(7,status);
            response.executeUpdate();
            conn.commit();
            conn.close();

        } catch (SQLException se) {
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

}

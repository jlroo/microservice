package edu.luc.cs439.system.contracts.models.maintenance;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
public class MaintenanceImpl implements Maintenance {
    private int facilityID;
    private LocalDate maintenanceStart;
    private LocalDate maintenanceEnd;
    private long facilityDowntime;
    private int maintenanceCost;
    private String maintenanceLog;
    private String MaintenanceStatus;

    public void setFacilityDowntime(LocalDate start, LocalDate end){
        this.facilityDowntime = Duration.between( start.atTime(0, 0),end.atTime(0, 0)).toMinutes();
    }
    public void setFacilityDowntime(Long downtime){
        this.facilityDowntime = downtime;
    }

    public static Maintenance FromJson(String json) {
        Gson gson = new Gson();
        Maintenance instance = gson.fromJson(json, Maintenance.class);
        return instance;
    }

    public static String ToJson(Collection<Maintenance> objects) {
        Maintenance[] allArray = (Maintenance[]) objects.toArray();
        String json = new Gson().toJson(allArray);
        return json;
    }

    public String ToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public LocalDate getMaintenanceStart() {
        return maintenanceStart;
    }

    public void setMaintenanceStart(LocalDate maintenanceStart) {
        this.maintenanceStart = maintenanceStart;
    }

    public LocalDate getMaintenanceEnd() {
        return maintenanceEnd;
    }

    public void setMaintenanceEnd(LocalDate maintenanceEnd) {
        this.maintenanceEnd = maintenanceEnd;
    }

    public long getFacilityDowntime() {
        return facilityDowntime;
    }

    public void setFacilityDowntime(long facilityDowntime) {
        this.facilityDowntime = facilityDowntime;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public String getMaintenanceLog() {
        return maintenanceLog;
    }

    public void setMaintenanceLog(String maintenanceLog) {
        this.maintenanceLog = maintenanceLog;
    }

    public String getMaintenanceStatus() {
        return MaintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        MaintenanceStatus = maintenanceStatus;
    }
}

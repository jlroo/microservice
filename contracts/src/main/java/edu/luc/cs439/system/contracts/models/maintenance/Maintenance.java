package edu.luc.cs439.system.contracts.models.maintenance;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

@JsonDeserialize(as=MaintenanceImpl.class)
public interface Maintenance {

    void setFacilityDowntime(LocalDate start, LocalDate end);
    void setFacilityDowntime(Long downtime);
    long getFacilityDowntime();

    int getFacilityID();
    void setFacilityID(int facilityID);

    LocalDate getMaintenanceStart();
    void setMaintenanceStart(LocalDate maintenanceStart);

    LocalDate getMaintenanceEnd();
    void setMaintenanceEnd(LocalDate maintenanceEnd);

    int getMaintenanceCost();
    void setMaintenanceCost(int maintenanceCost);

    String getMaintenanceLog();
    void setMaintenanceLog(String maintenanceLog);

    String getMaintenanceStatus();
    void setMaintenanceStatus(String maintenanceStatus);
}

package edu.luc.cs439.system.facility.service;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.contracts.models.maintenance.Maintenance;
import edu.luc.cs439.system.facility.dal.MaintenanceDAO;
import edu.luc.cs439.system.contracts.DefaultResponse;
import edu.luc.cs439.system.contracts.MaintenanceResponse;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
@RestController
@RequestMapping(value="/facility/maintenance/")
public class MaintenanceService {

    static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);
    private MaintenanceDAO _maintenanceDAO = new MaintenanceDAO();
    private Maintenance maintenance;

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    @RequestMapping(value="all",produces = "application/json")
    public String getAllMaintenance() {
        MaintenanceResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("GET").run();
            Collection<Maintenance> all = _maintenanceDAO.ReadAllMaintenance();
            response = MaintenanceResponse.Success(all);
        } catch (Exception e) {
            response = MaintenanceResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    @RequestMapping(value="{facilityID}",produces = "application/json")
    public String getMaintenance(@PathVariable("facilityID") int facilityID) {
        DefaultResponse response;
        try{
            ChaosSource.ForService("Maintenance").ForMethod("getMaintenanceByID").run();
            Collection<Maintenance>  facility = _maintenanceDAO.ReadAllMaintenance();
            Optional<Maintenance> match = facility
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = MaintenanceResponse.Success(new Maintenance[]{match.get()});
            } else {
                response = MaintenanceResponse.Error("Facility not found");
                LOG.error(response.Message);
            }
        } catch(Exception e){
            response = MaintenanceResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    @RequestMapping(value="add",produces = "application/json")
    public String addMaintenance(@RequestParam("id") int facilityID,
                                 @RequestParam("start") String startDate,
                                 @RequestParam("end") String endDate,
                                 @RequestParam("cost") int cost,
                                 @RequestParam(value = "log", required = false, defaultValue = "") String log){

        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("addMaintenance").run();

            Maintenance maintenance = getMaintenance();
            maintenance.setFacilityID(facilityID);

            String[] date0 = startDate.split("-");
            int year0 = Integer.parseInt(date0[0]);
            int month0 = Integer.parseInt(date0[1]);
            int day0 = Integer.parseInt(date0[2]);

            String[] date1 = endDate.split("-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            maintenance.setMaintenanceStart(LocalDate.of(year0, month0, day0));
            maintenance.setMaintenanceEnd(LocalDate.of(year1, month1, day1));
            maintenance.setFacilityDowntime(maintenance.getMaintenanceStart(), maintenance.getMaintenanceEnd());
            maintenance.setMaintenanceCost(cost);
            maintenance.setMaintenanceLog(log);

            if(maintenance.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _maintenanceDAO.Insert(maintenance);
                response = DefaultResponse.Success();
            }
        } catch(PSQLException e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        } catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }



    @RequestMapping(value="update",produces = "application/json")
    public String updateMaintenance(@RequestParam("id") int facilityID,
                                    @RequestParam("start") String startDate,
                                    @RequestParam("end") String endDate,
                                    @RequestParam("cost") int cost,
                                    @RequestParam(value = "log", required = false, defaultValue = "") String log) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("updateMaintenance").run();

            Maintenance maintenance = getMaintenance();
            maintenance.setFacilityID(facilityID);

            String[] date0 = startDate.split("-");
            int year0 = Integer.parseInt(date0[0]);
            int month0 = Integer.parseInt(date0[1]);
            int day0 = Integer.parseInt(date0[2]);

            String[] date1 = endDate.split("-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            maintenance.setMaintenanceStart(LocalDate.of(year0, month0, day0));
            maintenance.setMaintenanceEnd(LocalDate.of(year1, month1, day1));
            maintenance.setFacilityDowntime(maintenance.getMaintenanceStart(), maintenance.getMaintenanceEnd());
            maintenance.setMaintenanceCost(cost);
            maintenance.setMaintenanceLog(log);

            if(maintenance.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _maintenanceDAO.Update(facilityID,maintenance);
                response = DefaultResponse.Success();
            }
        } catch(PSQLException e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        } catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }



    @RequestMapping(value="delete/{deleteID}",produces = "application/json")
    public String deleteMaintenance(@PathVariable("deleteID") int deleteID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("deleteMaintenance").run();
            _maintenanceDAO.Delete(deleteID);
            response = DefaultResponse.Success();
        }
        catch(PSQLException e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }
}
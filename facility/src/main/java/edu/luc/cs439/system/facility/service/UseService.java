package edu.luc.cs439.system.facility.service;

import edu.luc.cs439.system.contracts.models.use.Inspection;
import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.facility.dal.UseDAO;
import edu.luc.cs439.system.contracts.models.use.FacilityUse;
import edu.luc.cs439.system.contracts.DefaultResponse;
import edu.luc.cs439.system.contracts.UseResponse;
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
@RequestMapping(value="facility/use/")
public class UseService {

    static final Logger LOG = LoggerFactory.getLogger(UseService.class);
    private UseDAO _useDAO = new UseDAO();
    private FacilityUse facility;

    public void setFacilityUse(FacilityUse facility) {
        this.facility = facility;
    }

    public FacilityUse getFacilityUse() {
        return facility;
    }


    @RequestMapping(value="all",produces = "application/json")
    public String getAllOrders() {
        UseResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("getAllOrders").run();
            Collection<FacilityUse> all = _useDAO.ReadAllOrders();
            response = UseResponse.Success(all);
        } catch (Exception e) {
            response = UseResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    @RequestMapping(value="{facilityID}",produces = "application/json")
    public String getFacilityOrder(@PathVariable("facilityID") int facilityID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("getOrder").run();
            Collection<FacilityUse> all = _useDAO.ReadAllOrders();
            Optional<FacilityUse> match = all
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = UseResponse.Success(new FacilityUse[]{match.get()});
            } else {
                response = DefaultResponse.Error("Order for facility not found");
                LOG.error(response.Message);
            }
        } catch (Exception e) {
            response = UseResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    @RequestMapping(value="add",produces = "application/json")
    public String addFacilityUse(@RequestParam("id") int facilityID,
                                 @RequestParam("customerID") int customer,
                                 @RequestParam("start") String startDate,
                                 @RequestParam("end") String endDate,
                                 @RequestParam("occupied") Boolean occupied,
                                 @RequestParam("inspectionDate") String inspectionDate,
                                 @RequestParam("code") String code,
                                 @RequestParam("passed") Boolean passed,
                                 @RequestParam("desc") String desc){
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("addFacilityUse").run();

            FacilityUse customerOrder = getFacilityUse();

            String[] date0 = startDate.split("-");
            int year0 = Integer.parseInt(date0[0]);
            int month0 = Integer.parseInt(date0[1]);
            int day0 = Integer.parseInt(date0[2]);

            String[] date1 = endDate.split("-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            String[] date3 = inspectionDate.split("-");
            int year3 = Integer.parseInt(date3[0]);
            int month3 = Integer.parseInt(date3[1]);
            int day3 = Integer.parseInt(date3[2]);

            customerOrder.setFacilityID(facilityID);
            customerOrder.setCustomerID(customer);
            customerOrder.setReservationStart(LocalDate.of(year0, month0, day0));
            customerOrder.setReservationEnd(LocalDate.of(year1, month1, day1));
            customerOrder.setOccupied(occupied);

            Inspection inspection = customerOrder.getInspection();

            inspection.setInspectionDate(LocalDate.of(year3, month3, day3));
            inspection.setInspectionCode(code);
            inspection.setPassedInspection(passed);
            inspection.setDescription(desc);

            customerOrder.setInspection(inspection);

            if(customerOrder.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
                LOG.error(response.Message);
            } else {
                _useDAO.Insert(customerOrder);
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
    public String updateFacilityUse(@RequestParam("id") int facilityID,
                                    @RequestParam("customerID") int customer,
                                    @RequestParam("start") String startDate,
                                    @RequestParam("end") String endDate,
                                    @RequestParam("occupied") Boolean occupied,
                                    @RequestParam("inspectionDate") String inspectionDate,
                                    @RequestParam("code") String code,
                                    @RequestParam("passed") Boolean passed,
                                    @RequestParam("desc") String desc) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("updateFacilityUse").run();

            FacilityUse customerOrder = getFacilityUse();

            String[] date0 = startDate.split("-");
            int year0 = Integer.parseInt(date0[0]);
            int month0 = Integer.parseInt(date0[1]);
            int day0 = Integer.parseInt(date0[2]);

            String[] date1 = endDate.split("-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            String[] date3 = inspectionDate.split("-");
            int year3 = Integer.parseInt(date3[0]);
            int month3 = Integer.parseInt(date3[1]);
            int day3 = Integer.parseInt(date3[2]);

            customerOrder.setFacilityID(facilityID);
            customerOrder.setCustomerID(customer);
            customerOrder.setReservationStart(LocalDate.of(year0, month0, day0));
            customerOrder.setReservationEnd(LocalDate.of(year1, month1, day1));
            customerOrder.setOccupied(occupied);

            Inspection inspection = customerOrder.getInspection();

            inspection.setInspectionDate(LocalDate.of(year3, month3, day3));
            inspection.setInspectionCode(code);
            inspection.setPassedInspection(passed);
            inspection.setDescription(desc);

            customerOrder.setInspection(inspection);

            if(customerOrder.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _useDAO.Update(facilityID,customerOrder);
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
    public String deleteFacilityUse(@PathVariable("deleteID") int deleteID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("deleteFacilityUse").run();
            _useDAO.Delete(deleteID);
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
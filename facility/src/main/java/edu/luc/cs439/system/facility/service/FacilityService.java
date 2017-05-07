package edu.luc.cs439.system.facility.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import edu.luc.cs439.system.contracts.models.facility.Details;
import edu.luc.cs439.system.contracts.models.facility.DetailsImpl;
import edu.luc.cs439.system.contracts.models.facility.Facility;
import edu.luc.cs439.system.contracts.models.facility.FacilityImpl;
import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.facility.dal.FacilityDAO;
import edu.luc.cs439.system.contracts.DefaultResponse;
import edu.luc.cs439.system.contracts.FacilityResponse;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping(value="/facility/")
public class FacilityService {

    static final Logger LOG = LoggerFactory.getLogger(FacilityService.class);

    private FacilityDAO _facilityDAO = new FacilityDAO();

    public Facility getFacility() {return new FacilityImpl();}

    public Details getDetails() {return new DetailsImpl();}

    @RequestMapping(value = "all",produces = "application/json")
    public String getAllFacilities() {
        FacilityResponse response;
        try{
            ChaosSource.ForService("Facility").ForMethod("getAllFacilities").run();
            Collection<Facility> all = _facilityDAO.ReadAllFacilities();
            response = FacilityResponse.Success(all);
        } catch(Exception e) {
            response = FacilityResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }



    @RequestMapping(value="{facilityID}", produces = "application/json")
    public String getFacility(@PathVariable("facilityID") int facilityID) {
        FacilityResponse response;
        try{
            ChaosSource.ForService("Facility").ForMethod("getFacility").run();
            Collection<Facility>  facility = _facilityDAO.ReadAllFacilities();
            Optional<Facility> match = facility
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = FacilityResponse.Success(new Facility[]{match.get()});
            } else {
                response = FacilityResponse.Success("Facility not found");
                LOG.error(response.Message);
            }
        } catch(Exception e){
            response = FacilityResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    @RequestMapping(value="add",method = RequestMethod.GET, produces = "application/json")
    public String addFacility(@RequestParam("id")  int facilityID,
                              @RequestParam("room") int room,
                              @RequestParam("media") Boolean media,
                              @RequestParam("capacity") int capacity,
                              @RequestParam("name") String name,
                              @RequestParam("phone") String phone,
                              @RequestParam("dept") String dept,
                              @RequestParam("occupied") Boolean occupied,
                              @RequestParam("date") String date){
        
        DefaultResponse response;

        try {

            ChaosSource.ForService("Facility").ForMethod("addFacility").run();

            Facility newObj = getFacility();
            newObj.setFacilityID(facilityID);
            newObj.setRoomNumber(room);
            newObj.setMedia(media);
            newObj.setMaxCapacity(capacity);
            newObj.setName(name);

            Details newObjDetails = getDetails();

            newObjDetails.setFacilityID(newObj.getFacilityID());
            newObjDetails.setPhoneNumber(phone);
            newObjDetails.setDepartment(dept);
            newObjDetails.setOccupied(occupied);

            String[] localDate = date.split("-");
            int year = Integer.parseInt(localDate[0]);
            int month = Integer.parseInt(localDate[1]);
            int day = Integer.parseInt(localDate[2]);
            newObjDetails.setInspected(LocalDate.of(year,month,day));
            newObj.setDetails(newObjDetails);

            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Success("Error, ID did not match");
            } else {
                _facilityDAO.Insert(newObj);
                response = DefaultResponse.Success();
            }
        } catch(PSQLException e) {
            if(e.getMessage().contains("duplicate")){
                response = DefaultResponse.Success("Facility with id: " + facilityID + " already exists!");
                LOG.error(response.Message);
            } else {
                response = DefaultResponse.Error(e);
                LOG.error(response.Message);
            }
        } catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    @RequestMapping(value="update", method = RequestMethod.GET, produces = "application/json")
    public String updateFacility(@RequestParam(value = "id")                        int id,
                                 @RequestParam(value = "room")                      int room,
                                 @RequestParam(value = "media",     required=false) Boolean media,
                                 @RequestParam(value = "capacity",  required=false) int capacity,
                                 @RequestParam(value = "name",      required=false) String name,
                                 @RequestParam(value = "phone",     required=false) String phone,
                                 @RequestParam(value = "dept",      required=false) String dept,
                                 @RequestParam(value = "occupied",  required=false) Boolean occupied,
                                 @RequestParam(value = "date",      required=false) String date){
        DefaultResponse response;
        try {

            ChaosSource.ForService("Facility").ForMethod("updateFacility").run();

            Facility newObj = getFacility();
            newObj.setFacilityID(id);
            newObj.setRoomNumber(room);
            newObj.setMedia(media);
            newObj.setMaxCapacity(capacity);
            newObj.setName(name);

            Details newObjDetails = getDetails();

            newObjDetails.setFacilityID(newObj.getFacilityID());
            newObjDetails.setPhoneNumber(phone);
            newObjDetails.setDepartment(dept);
            newObjDetails.setOccupied(occupied);

            String[] localDate = date.split("-");
            int year = Integer.parseInt(localDate[0]);
            int month = Integer.parseInt(localDate[1]);
            int day = Integer.parseInt(localDate[2]);
            newObjDetails.setInspected(LocalDate.of(year,month,day));
            newObj.setDetails(newObjDetails);

            if(newObj.getFacilityID() != id) {
                response = DefaultResponse.Success("Error, ID did not match");
                LOG.error(response.Message);
            } else {
                _facilityDAO.Update(id,newObj);
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



    @RequestMapping(value="delete/{facilityID}", produces = "application/json")
    public String deleteFacility(@PathVariable("facilityID") int deleteID) {
        DefaultResponse response;
        try {

            ChaosSource.ForService("Facility").ForMethod("deleteFacility").run();

            _facilityDAO.Delete(deleteID);
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



    @RequestMapping(value="delete/all",produces = "application/json")
    public String cleanDB() {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Facility").ForMethod("DeleteDB").run();
            _facilityDAO.cleanTables();
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
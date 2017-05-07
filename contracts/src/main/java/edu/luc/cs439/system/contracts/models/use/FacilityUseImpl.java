package edu.luc.cs439.system.contracts.models.use;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
public class FacilityUseImpl implements FacilityUse {

    private int facilityID;
    private int orderNumber;
    private LocalDate reservationStart;
    private LocalDate reservationEnd;
    private boolean occupied;
    private int customerID;

    @Autowired
    private Inspection inspection;

    public Inspection getInspection() {
        return inspection;
    }
    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }


    public static FacilityUse FromJson(String json) {
        Gson gson = new Gson();
        FacilityUse instance = gson.fromJson(json, FacilityUse.class);
        return instance;
    }

    public static String ToJson(Collection<FacilityUse> objects) {
        FacilityUse[] allArray = (FacilityUse[]) objects.toArray();
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDate reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDate getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDate reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}

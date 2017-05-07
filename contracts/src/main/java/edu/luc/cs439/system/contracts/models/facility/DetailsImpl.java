package edu.luc.cs439.system.contracts.models.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
public class DetailsImpl implements Details {

    private int facilityID;
    private String phoneNumber;
    private String department;
    private boolean occupied;
    private LocalDate inspected;

    @Autowired
    private Details details;

    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {this.details = details;}

    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public LocalDate getInspected() {
        return inspected;
    }

    public void setInspected(LocalDate inspected) {
        this.inspected = inspected;
    }
}

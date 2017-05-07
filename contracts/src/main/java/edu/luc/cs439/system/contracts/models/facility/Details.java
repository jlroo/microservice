package edu.luc.cs439.system.contracts.models.facility;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

@JsonDeserialize(as=DetailsImpl.class)
public interface Details {

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    String getDepartment();
    void setDepartment(String department);

    boolean isOccupied();
    void setOccupied(boolean occupied);

    LocalDate getInspected();
    void setInspected(LocalDate inspected);

    int getFacilityID();
    void setFacilityID(int facilityID);
}

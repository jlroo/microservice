package edu.luc.cs439.system.contracts.models.use;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */


@JsonDeserialize(as=FacilityUseImpl.class)
public interface FacilityUse {

    void setInspection(Inspection inspection);
    Inspection getInspection();

    int getOrderNumber();
    void setOrderNumber(int orderNumber);

    LocalDate getReservationStart();
    void setReservationStart(LocalDate reservationStart);

    LocalDate getReservationEnd();
    void setReservationEnd(LocalDate reservationEnd);

    boolean isOccupied();
    void setOccupied(boolean occupied);

    int getCustomerID();
    void setCustomerID(int customerID);

    int getFacilityID();
    void setFacilityID(int facilityID);
}

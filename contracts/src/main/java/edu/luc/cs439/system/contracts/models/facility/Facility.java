package edu.luc.cs439.system.contracts.models.facility;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by jlroo on 3/21/17.
 */


@JsonDeserialize(as=FacilityImpl.class)
public interface Facility{

    int getFacilityID();
    void setFacilityID(int facilityID);

    String getName();
    void setName(String name);

    int getRoomNumber();
    void setRoomNumber(int roomNumber);

    boolean isMedia();
    void setMedia(boolean media);

    int getMaxCapacity();
    void setMaxCapacity(int maxCapacity);

    Details getDetails();
    void setDetails(Details details);

}

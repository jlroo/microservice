package edu.luc.cs439.system.contracts.models.facility;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class FacilityImpl implements Facility {

    private int facilityID;
    private String name;
    private int roomNumber;
    private boolean media;
    private int maxCapacity;

    @Autowired
    private Details details;

    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {
        this.details = details;
    }

    public static Facility FromJson(String json) {
        Gson gson = new Gson();
        Facility instance = gson.fromJson(json, Facility.class);
        return instance;
    }

    public static String ToJson(Collection<Facility> objects) {
        Facility[] allArray = (Facility[]) objects.toArray();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isMedia() {
        return media;
    }

    public void setMedia(boolean media) {
        this.media = media;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}

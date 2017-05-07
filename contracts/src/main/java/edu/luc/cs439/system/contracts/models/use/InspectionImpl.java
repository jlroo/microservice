package edu.luc.cs439.system.contracts.models.use;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by jlroo on 2/20/17.
 */

public class InspectionImpl implements Inspection {

    private String inspectionCode;
    private LocalDate inspectionDate;
    private Boolean passedInspection;
    private String description;

    public static Inspection FromJson(String json) {
        Gson gson = new Gson();
        Inspection instance = gson.fromJson(json, Inspection.class);
        return instance;
    }

    public static String ToJson(Collection<Inspection> objects) {
        Inspection[] allArray = (Inspection[]) objects.toArray();
        String json = new Gson().toJson(allArray);
        return json;
    }

    public String ToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Boolean getPassedInspection() {
        return passedInspection;
    }

    public void setPassedInspection(Boolean passedInspection) {
        this.passedInspection = passedInspection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

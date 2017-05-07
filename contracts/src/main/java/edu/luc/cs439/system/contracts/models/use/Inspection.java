package edu.luc.cs439.system.contracts.models.use;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

@JsonDeserialize(as=InspectionImpl.class)
public interface Inspection {

    String getInspectionCode();
    void setInspectionCode(String inspectionCode);

    LocalDate getInspectionDate();
    void setInspectionDate(LocalDate inspectionDate);

    Boolean getPassedInspection();
    void setPassedInspection(Boolean passedInspection);

    String getDescription();
    void setDescription(String description);
}

package edu.luc.cs439.system.contracts;

import com.google.gson.Gson;
import edu.luc.cs439.system.contracts.models.facility.Facility;
import edu.luc.cs439.system.contracts.models.use.FacilityUse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class FacilityResponse extends DefaultResponse {

	@Autowired
	public final Facility[] facilityResponse;
	
	public static FacilityResponse Error(Exception e) {
		String stackTrace = ExceptionTraceReader.ReadTrace(e);
		return new FacilityResponse(false, e.getMessage(), stackTrace, new Facility[0]);
	}
	
	public static FacilityResponse Error(String message) {
		return new FacilityResponse(false, message, "", new Facility[0]);
	}

	public static FacilityResponse Success(String message) {
		return new FacilityResponse(true, message, "", new Facility[0]);
	}
	
	public static FacilityResponse Success(FacilityUse[] facilityUses) {
	    return new FacilityResponse(true, "", "", new Facility[0]);
	}
	
	public static FacilityResponse Success(Collection<Facility> facilityResponse) {
		Facility[] array = new Facility[facilityResponse.size()];
		int index = -1;
		for(Facility c : facilityResponse) {
			array[++index] = c;
		}
		return Success(array);
	}
	
	public static FacilityResponse Success(Facility[] facilityResponse) {
		return new FacilityResponse(true, "", "", facilityResponse);
	}
	
	public static FacilityResponse FromJson(String json) {
		Gson serializer = new Gson();
		FacilityResponse response = serializer.fromJson(json, FacilityResponse.class);
		return response;
	}

	private FacilityResponse(boolean success, String message, String trace, Facility[] facility){
		super(success, message, trace);
		facilityResponse = facility;
	}
	
	public FacilityResponse() {
		super();
		facilityResponse = new Facility[0];
	}
}

package edu.luc.cs439.system.contracts;

import com.google.gson.Gson;
import edu.luc.cs439.system.contracts.models.use.FacilityUse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UseResponse extends DefaultResponse {

	@Autowired
	public final FacilityUse[] orderResponse;

	public static UseResponse Error(Exception e) {
		String trace = ExceptionTraceReader.ReadTrace(e);
		return new UseResponse(false, e.getMessage(), trace, new FacilityUse[0]);
	}

	public static UseResponse NoMatchFound(long id) {
		return new UseResponse(false, "Could not find order " + id, "", new FacilityUse[0]);
	}

    public static UseResponse Success(Collection<FacilityUse> all) {
		FacilityUse[] array = new FacilityUse[all.size()];
		int index = -1;
		for(FacilityUse c : all) {
			array[++index] = c;
		}
		return new UseResponse(true, "", "", array);
	}
	
	public static UseResponse FromJson(String json) {
		Gson serializer = new Gson();
		UseResponse response = serializer.fromJson(json, UseResponse.class);
		return response;
	}

    public static UseResponse Success(FacilityUse[] orderResponse) {
        return new UseResponse(true, "", "", orderResponse);
    }

	private UseResponse(boolean success, String message, String trace, FacilityUse[] orders){
		super(success, message, trace);
        orderResponse = orders;
	}
	
	private UseResponse() {
		super();
        orderResponse = new FacilityUse[0];
	}

}

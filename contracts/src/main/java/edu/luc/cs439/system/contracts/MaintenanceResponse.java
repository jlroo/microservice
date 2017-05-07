package edu.luc.cs439.system.contracts;

import com.google.gson.Gson;
import edu.luc.cs439.system.contracts.models.maintenance.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created by jlroo on 3/24/17.
 */

public class MaintenanceResponse extends DefaultResponse {

    @Autowired
    public final Maintenance[] maintenanceResponse;

    public static MaintenanceResponse Error(Exception e) {
        String trace = ExceptionTraceReader.ReadTrace(e);
        return new MaintenanceResponse(false, e.getMessage(), trace, new Maintenance[0]);
    }

    public static MaintenanceResponse NoMatchFound(long id) {
        return new MaintenanceResponse(false, "Could not find order " + id, "", new Maintenance[0]);
    }

    public static MaintenanceResponse Success(Collection<Maintenance> all) {
        Maintenance[] array = new Maintenance[all.size()];
        int index = -1;
        for(Maintenance c : all) {
            array[++index] = c;
        }
        return new MaintenanceResponse(true, "", "", array);
    }

    public static MaintenanceResponse Success(Maintenance[] maintenanceResponse) {
        return new MaintenanceResponse(true, "", "", maintenanceResponse);
    }

    public static MaintenanceResponse FromJson(String json) {
        Gson serializer = new Gson();
        MaintenanceResponse response = serializer.fromJson(json, MaintenanceResponse.class);
        return response;
    }

    private MaintenanceResponse(boolean success, String message, String trace, Maintenance[] orders){
        super(success, message, trace);
        maintenanceResponse = orders;
    }

    private MaintenanceResponse() {
        super();
        maintenanceResponse = new Maintenance[0];
    }
}

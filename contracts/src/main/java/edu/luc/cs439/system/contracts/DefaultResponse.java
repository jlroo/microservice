package edu.luc.cs439.system.contracts;

import com.google.gson.Gson;
import java.io.PrintWriter;
import java.io.StringWriter;

public class DefaultResponse {
	public final boolean Success;
	public final String Message;
	public final String StackTrace;
	
	public static DefaultResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new DefaultResponse(false, e.getMessage(), stackTrace);
	}
	
	public static DefaultResponse Error(String message) {
		return new DefaultResponse(false, message, "");
	}

	public static DefaultResponse Success(String message) {
		return new DefaultResponse(true, message, "");
	}

	public static DefaultResponse Success() {
		return new DefaultResponse(true, "", "");
	}
	
	protected DefaultResponse(boolean success, String message, String stackTrace) {
		Success = success;
		Message = message;
		StackTrace = stackTrace;
	}
	
	protected DefaultResponse() {
		Success = true;
		Message = "";
		StackTrace = "";
	}
	
	public String ToJson() {
		Gson serializer = new Gson();
		String json = serializer.toJson(this);
		return json;
	}
	
	public static DefaultResponse FromJson(String json) {
		Gson serializer = new Gson();
		DefaultResponse response = serializer.fromJson(json, DefaultResponse.class);
		return response;
	}
}

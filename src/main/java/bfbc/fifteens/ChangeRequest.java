package bfbc.fifteens;

import com.google.gson.annotations.Expose;

public class ChangeRequest {

	@Expose
	private String command;
	@Expose
	private int index;
	
	public int getIndex() {
		return index;
	}

	public String getCommand() {
		return command;
	}

	public ChangeRequest(String command, int index) {
		this.command = command;
		this.index = index;
	}
	
	public static ChangeRequest fromJson(String json) {
		return GlobalServices.getGson().fromJson(json, ChangeRequest.class);
	}
}

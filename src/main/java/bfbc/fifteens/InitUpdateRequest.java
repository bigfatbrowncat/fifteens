package bfbc.fifteens;

import com.google.gson.annotations.Expose;

public class InitUpdateRequest {

	@Expose
	private int size;
	@Expose
	private int[] data;

	public int getSize() {
		return size;
	}

	public int[] getData() {
		return data.clone();
	}

	public InitUpdateRequest(int size, int[] data) {
		this.size = size;
		this.data = data;
	}

	public String toJson() {
		return GlobalServices.getGson().toJson(this);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}

package bfbc.fifteens;

import com.google.gson.annotations.Expose;

import bfbc.fifteens.core.Direction;

public class MoveUpdateRequest {

	@Expose
	private int index;
	@Expose
	private Direction direction;
	
	public int getIndex() {
		return index;
	}

	public Direction getDirection() {
		return direction;
	}

	public MoveUpdateRequest(int index, Direction direction) {
		this.index = index;
		this.direction = direction;
	}

	public String toJson() {
		return GlobalServices.getGson().toJson(this);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}

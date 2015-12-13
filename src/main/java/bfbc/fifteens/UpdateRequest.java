package bfbc.fifteens;

import com.google.gson.annotations.Expose;

public class UpdateRequest {
	public enum Type {
		Init, Move;
	}

	@Expose
	private Type type;
	@Expose
	private InitUpdateRequest initUpdateRequest;
	@Expose
	private MoveUpdateRequest moveUpdateRequest;

	public InitUpdateRequest getInitUpdateRequest() {
		return initUpdateRequest;
	}

	public MoveUpdateRequest getMoveUpdateRequest() {
		return moveUpdateRequest;
	}

	public UpdateRequest(InitUpdateRequest initUpdateRequest) {
		this.type = Type.Init;
		this.initUpdateRequest = initUpdateRequest;
	}
	
	public UpdateRequest(MoveUpdateRequest moveUpdateRequest) {
		this.type = Type.Move;
		this.moveUpdateRequest = moveUpdateRequest;
	}
	
	public String toJson() {
		return GlobalServices.getGson().toJson(this);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}

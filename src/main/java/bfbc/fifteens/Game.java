package bfbc.fifteens;

import bfbc.fifteens.core.Direction;
import bfbc.fifteens.core.FifteensField;

public class Game {
	private FifteensField field;
	public synchronized void startNew() {
		field = new FifteensField();
	}
	
	public boolean isStarted() {
		return field != null;
	}
	
	public synchronized Direction findWayAndMoveXY(int x, int y, boolean doMove) {
		return field.findWayAndMoveXY(x, y, doMove);
	}

	public synchronized Direction findWayAndMove(int num, boolean doMove) {
		return field.findWayAndMove(num, doMove);
	}
	
	public synchronized Integer getIndexForDirection(Direction dir, int index) {
		return field.getIndexForDirection(dir, index);
	}

	public synchronized FifteensField cloneField() {
		return field.clone();
	}
	
	public synchronized int getNum(int i) {
		return field.getNum(i);
	}
	
	public synchronized InitUpdateRequest createInitUpdateRequest() {
		return field.createInitUpdateRequest();
	}
	
	
}

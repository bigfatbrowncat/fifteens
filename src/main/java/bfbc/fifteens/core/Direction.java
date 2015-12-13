package bfbc.fifteens.core;

public enum Direction {
	Left(0),
	Up(1),
	Right(2),
	Down(3);
	
	public final int id;
	
	Direction(int id) {
		this.id = id;
	}
	
	public static Direction fromId(int id) {
		for (Direction v : values()) {
			if (v.id == id) {
				return v;
			}
		}
		return null;
	}
}
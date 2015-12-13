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
}
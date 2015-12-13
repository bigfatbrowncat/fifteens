package bfbc.fifteens.core;

import bfbc.fifteens.InitUpdateRequest;

public class FifteensField {
	
	//   0 1 2 3
	// 0 0 1 2 3
	// 1 4 5 6 7
	// 2 8 9 A B
	// 3 C D E F 
	
	private int size;
	private int[] data;
	
	public void init() {
		int index = 0;
		for (int i = 0; i < size * size; i++) {
			data[i] = index;
			index ++;
		}
	}
	
	public FifteensField(int size) {
		this.size = size;
		data = new int[size * size];
		init();
	}
	
	public FifteensField() {
		this(4);
	}
	
	private int toX(int i) {
		return i % 4;
	}
	private int toY(int i) {
		return i / 4;
	}
	private int toI(int x, int y) {
		return y * 4 + x;
	}
	
	public int findI(int num) {
		for (int i = 0; i < size*size; i++) {
			if (data[i] == num) return i;
		}
		throw new RuntimeException("Invalid field");
	}
	
	public Direction findWayAndMove(int num, boolean doMove) {
		int i = findI(num);
		Integer l = null, r = null, u = null, d = null;
		int x = toX(i), y = toY(i);
		if (x > 0) l = toI(x - 1, y);
		if (x < size - 1) r = toI(x + 1, y);
		if (y > 0) u = toI(x, y - 1);
		if (y < size - 1) d = toI(x, y + 1);
		
		if (l != null && data[l] == 0) { 
			if (doMove) {
				data[l] = data[i];
				data[i] = 0;
			}
			return Direction.Left;
		}
		if (r != null && data[r] == 0) {
			if (doMove) {
				data[r] = data[i];
				data[i] = 0;
			}
			return Direction.Right;
		}
		if (u != null && data[u] == 0) {
			if (doMove) {
				data[u] = data[i];
				data[i] = 0;
			}
			return Direction.Up;
		}
		if (d != null && data[d] == 0) {
			if (doMove) {
				data[d] = data[i];
				data[i] = 0;
			}
			return Direction.Down;
		}
		
		return null;
	}
	
	public InitUpdateRequest createInitUpdateRequest() {
		return new InitUpdateRequest(size, data);
	}
}

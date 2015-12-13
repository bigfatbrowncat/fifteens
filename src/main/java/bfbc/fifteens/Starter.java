package bfbc.fifteens;

import static spark.Spark.*;

import bfbc.fifteens.core.FifteensField;

public final class Starter {

	public static class Game {
		private FifteensField field;
		public synchronized void startNew() {
			field = new FifteensField();
		}
		
		public FifteensField getField() {
			return field;
		}
	}
	
	public static Game game = new Game();
	
	public static void main(String[] args) {
		port(9876);
		staticFileLocation("static_root");
		
    	webSocket("/wsapi", FifteensWebSocket.class);

		init();
	}

}

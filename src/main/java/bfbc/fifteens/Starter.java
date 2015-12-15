package bfbc.fifteens;

import static spark.Spark.*;

import bfbc.fifteens.core.FifteensField;

public final class Starter {

	
	public static Game game = new Game();
	
	public static void main(String[] args) {
		port(9876);
		staticFileLocation("static_root");
		
    	webSocket("/wsapi", FifteensWebSocket.class);

		init();
	}

}

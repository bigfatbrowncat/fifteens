package bfbc.fifteens;

import static spark.Spark.*;

import bfbc.fifteens.core.FifteensField;

public final class Starter {

	public final static FifteensField field = new FifteensField();
	
	public static void main(String[] args) {
		port(9876);
		staticFileLocation("static_root");
		
    	webSocket("/wsapi", FifteensWebSocket.class);

		/*get("/", (request, response) -> {
			return "Hello, world!";
		});*/
		
		init();
	}

}

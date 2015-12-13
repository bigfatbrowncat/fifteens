package bfbc.fifteens;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalServices {
	private static Gson gson;
	
	static {
    	GsonBuilder builder = new GsonBuilder();
    	builder.excludeFieldsWithoutExposeAnnotation();
    	gson = builder.create();
	}
	
	public static Gson getGson() {
		return gson;
	}
}

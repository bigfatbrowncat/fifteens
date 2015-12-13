package bfbc.fifteens;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GlobalServices {
	private static Gson gson;
    private static Random random = new Random();
	
	static {
    	GsonBuilder builder = new GsonBuilder();
    	builder.excludeFieldsWithoutExposeAnnotation();
    	gson = builder.create();
	}
	
	public static Gson getGson() {
		return gson;
	}
	
	public static Random getRandom() {
		return random;
	}
}

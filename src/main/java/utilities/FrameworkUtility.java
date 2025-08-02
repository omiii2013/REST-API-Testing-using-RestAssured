package utilities;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.response.Response;


public abstract class FrameworkUtility {
	
	protected static Properties config;

	public static String readConfigurationFile(String key) {
		
		try{
			config = new Properties();
			config.load(new FileInputStream(FrameworkUtility_String.CONFIG_FILE_PATH));
			
		} catch (Exception e){
			System.out.println("Cannot find key: "+key+" in Config file due to exception : "+e);
		}
		
		return config.getProperty(key).trim();	
	}
	
	public static JSONObject getJSONObject(String filePath) {
		
		// To get the JSON request from external file			
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		try {
			obj = parser.parse(new FileReader(filePath));
		} catch (Exception e) {
			AllureLogger.logToAllure("Error in JSON object parsing with exception : "+e);
			
		}
		
		return (JSONObject) obj;
	}
	
	/*******************************************************
	 * Print the response JSON
	 ******************************************************/

	public void logResponseAsString(Response response) {
		AllureLogger.logToAllure(response.asString());
		System.out.println(response.asString());
		
	}
	
	/*******************************************************
	 * Print the all output log along with the response json (headers, cookies etc)
	 ******************************************************/
	
	public void printOutputLog(Response response) {
		response.then().log().all();		
	}
}

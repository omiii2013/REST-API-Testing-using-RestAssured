package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.AllureLogger;
import utilities.BaseTest;
import utilities.FrameworkUtility_String;
import utilities.FrameworkUtility;

import org.json.simple.JSONObject;

import static org.hamcrest.Matchers.*; // import this 
import static io.restassured.RestAssured.*; //import this


public class AuthToken extends BaseTest {
	
    
	public static String postCreateAuth(){
		
		AllureLogger.logToAllure("Starting the test for POST method for create authentication");
		
		/*******************************************************
		 * Send a POST request to /auth
		 * and check that the response has HTTP status code 200
		 ******************************************************/
		JSONObject jsonObject = getJSONObject(FrameworkUtility_String.POSTRequest_AUTH_DEFAULT_REQUEST);
		
		String username = readConfigurationFile("username");
		String password = readConfigurationFile("password");
		
		jsonObject.put("password", password);
		jsonObject.put("username", username);
		
		AllureLogger.logToAllure("Username from config file is : \n"+ username);
		AllureLogger.logToAllure("Password from config file is : \n"+ password);
		
		
		Response response = given().
								spec(requestSpec).
								contentType("application/json").
								body(jsonObject.toJSONString()).
							when().
								post("/auth");
		
		AllureLogger.logToAllure("Asserting the response if the status code returned is 200");
		response.then().spec(responseSpec);
		
		
		String token = response.then().extract().path("token");
		return token;
	}
}

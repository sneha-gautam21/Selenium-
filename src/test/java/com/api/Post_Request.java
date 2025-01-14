package com.api;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Post_Request {

	    public static void main(String[] args) {
	        // Set the base URI for the API
	        RestAssured.baseURI = "https://api.restful-api.dev";

	        // Prepare the JSON body to be sent in the POST request
	        String jsonBody = "{\n" +
	                          "   \"name\": \"Apple MacBook Pro 16\",\n" +
	                          "   \"data\": {\n" +
	                          "      \"year\": 2019,\n" +
	                          "      \"price\": 1849.99,\n" +
	                          "      \"CPU model\": \"Intel Core i9\",\n" +
	                          "      \"Hard disk size\": \"1 TB\"\n" +
	                          "   }\n" +
	                          "}";

	        // Send the POST request to /objects with the JSON body
	        Response response = RestAssured.given()
	            .contentType("application/json").body(jsonBody).when().post("/objects");  

	        // Print the response status code
	        System.out.println("Status Code: " + response.getStatusCode());

	        // Print the response body 
	        System.out.println("Response Body: ");
	        response.prettyPrint();

	        // Check that the status code is 200 (OK)
	        if (response.getStatusCode() == 200) {
	            System.out.println("Object created successfully.");
	        }
	        else
	        {
	            System.out.println("Error: API returned status code " + response.getStatusCode());
	        }
	    }
	}

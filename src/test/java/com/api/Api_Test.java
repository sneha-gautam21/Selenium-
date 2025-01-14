package com.api;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Api_Test {

	    public static void main(String[] args) {
	        // Set the base URI for the API
	        RestAssured.baseURI = "https://api.restful-api.dev";

	        // Send the GET request to the /objects end point
	        Response response = RestAssured.given().when().get("/objects");

	        // Print the response status code
	        System.out.println("Status Code: " + response.getStatusCode());

	        // Print the response body (usually in JSON format)
	        System.out.println("Response Body: " + response.getBody().asString());

	        // For example, check that the status code is 200 (OK)
	        if (response.getStatusCode() == 200) {
	        	System.out.println();
	            System.out.println("API is working fine.");
	        } else {
	            System.out.println("Error: API returned status code " + response.getStatusCode());
	        }
	    }
	}



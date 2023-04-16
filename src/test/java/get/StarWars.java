package get;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.StarWarsResultPojo;
import pojo.StarWarsPojo;
import utils.Shortcut;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarWars {

    /*
    1.Define/determined the endpoint
    2.Added query String parameters as needed
    3.Defined HTTP Method
    4.Send
    5.Validate status code
     */

    @Test
    public void getSWChars() {
//        //https://swapi.dev/api/people
//        RestAssured.given().when().get("https://swapi.dev/api/people").then().statusCode(200).log().body();
        Shortcut.getAPICode("https://swapi.dev/api/people", 200);
    }

    @Test
    public void getSWCharsDeserialized() {
        //Shortcut.getAPICodeByHeader("Accept","application/json","https://swapi.dev/api/people",200);
        Response response = RestAssured.given().header("Accept", "application/json").when()
                .get("https://swapi.dev/api/people").then().statusCode(200).extract().response();

        Map<String, Object> deserializedResponse = response.as(new TypeRef<>() {
        });

        System.out.println(deserializedResponse.get("count"));
        System.out.println(deserializedResponse.get("next"));

        int count = (int) deserializedResponse.get("count");
        Assert.assertEquals(82, count);

        // Array of JSON objects [ {}, {}, {}, {} ]
        List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse.get("results");
        // System.out.println(results);

        List<String> names = new ArrayList<>();
        List<String> femaleNames = new ArrayList<>();
        List<String> maleNames = new ArrayList<>();
        //List<Map<String ,Object>> result = (List<Map<String, Object>>) deserializedResponse.get("results");
        //Set<Map.Entry<String ,Object>> pairs = result.entrySet();
        for (Map<String, Object> individual : results) {
              System.out.println(individual.get("name"));
            names.add((String) individual.get("name"));
            if (individual.get("gender").toString().contains("female")) {
                // System.out.println("Female: "+individual.get("name"));
                femaleNames.add((String) individual.get("name"));
            } else if (individual.get("gender").toString().contains("male")) {
                // System.out.println("Male: "+individual.get("name"));
                maleNames.add((String) individual.get("name"));
            }
        }
        System.out.println("List of all names: " + names);
        System.out.println("List of female names: " + femaleNames);
        System.out.println("List of male names: " + maleNames);
    }

    /*
    HW:
	- validate that SW API Count value is correct, we have total of 82 characters.
	- get list of all SW characters name
	- LVL100: Find only characters gender is female:
	Map<String, List<String>> -> female:
     */

    //Validate that SW API Count value is correct, we have total of 82 character
    @Test
    public void resultsCountsValidation() {

        String originalPageLink = "https://swapi.dev/api/people/?page=";
        String dynamicPageLink = "https://swapi.dev/api/people/?page=";
        int ActualSWApiCount = 0;

        for (int i = 1; i <= 9; i++) {
            dynamicPageLink = dynamicPageLink.concat(String.valueOf(i));

            Response response1 = RestAssured.given().header("Accept", "application/json")
                    .when().get(dynamicPageLink)
                    .then().statusCode(200).extract().response();

            Map<String, Object> deserializedResponse1 = response1.as(new TypeRef<Map<String, Object>>() {
            });

            List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse1.get("results");

            ActualSWApiCount = ActualSWApiCount + results.size();

            dynamicPageLink = originalPageLink;
        }

        Assert.assertEquals(82, ActualSWApiCount);
    }

    //-get list of all SW characters name
    @Test
    public void allCharactersName() {
        String originalPageLink = "https://swapi.dev/api/people/?page=";
        String dynamicPageLink = "https://swapi.dev/api/people/?page=";
        for (int i = 1; i <= 9; i++) {
            dynamicPageLink = dynamicPageLink.concat(String.valueOf(i));
            Response response = RestAssured.given().header("Accept", "application/json")
                    .when().get(dynamicPageLink)
                    .then().statusCode(200).extract().response();

            Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
            });

            List<Map<String, Object>> result = (List<Map<String, Object>>) deserializedResponse.get("results");
            System.out.println("page " + i + "/");
            for (Map<String, Object> character : result) {

                System.out.println(character.get("name"));

            }
            dynamicPageLink = originalPageLink;
        }

    }

    //-get list all female characters name
    @Test
    public void allFemaleCharacters() {

        String originalPageLink = "https://swapi.dev/api/people/?page=";
        String dynamicPageLink = "https://swapi.dev/api/people/?page=";
        for (int i = 1; i <= 9; i++) {
            dynamicPageLink = dynamicPageLink.concat(String.valueOf(i));
            Response response = RestAssured.given().header("Accept", "application/json")
                    .when().get(dynamicPageLink)
                    .then().statusCode(200).extract().response();

            Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
            });

            List<Map<String, Object>> result = (List<Map<String, Object>>) deserializedResponse.get("results");
          //  System.out.println("page " + i + "/");
            for (Map<String, Object> character : result) {
                if (character.get("gender").equals("female")) {
                    System.out.println(character.get("name"));
                }
            }
            dynamicPageLink = originalPageLink;
        }
    }

    @Test
    public void swAPIGetWithPojo(){
       Response response =  RestAssured.given().header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people").then().statusCode(200).extract().response();

       StarWarsPojo deserializedResp = response.as(StarWarsPojo.class);
       int actualCount = deserializedResp.getCount();
       int expectedCount = 82;
       Assert.assertEquals(expectedCount,actualCount);

        List<StarWarsResultPojo> results = deserializedResp.getResults();
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i).getName());
//            System.out.println(results.get(i).getGender());
        }
    }

    @Test
    public void StarWarsTest(){
        //https://swapi.dev/api/people

        RestAssured.baseURI = "https://swapi.dev";
        RestAssured.basePath = "api/people";
        //RestAssured.given().header("Accept","application/json").
       Response response = RestAssured.given().accept(ContentType.JSON)//.log().all() //--> prints ONLY ALL request
                .when().get().then().statusCode(200)//.log().all() // prints ALL
                .extract().response();

        StarWarsPojo parsedResponse = response.as(StarWarsPojo.class);

        int actualTotalCharactersCount = parsedResponse.getResults().size(); // counting characters from first page
        System.out.println(actualTotalCharactersCount);
        String nextUrl = parsedResponse.getNext(); // gets value of "next" field from JSON response

        //1. Make a GET request to nextUrl
        //2. Count characters from next page
        //3. Get next page URL
        while (nextUrl != null) {
            //1. Make a GET request to nextUrl
            response = RestAssured.given().accept(ContentType.JSON)
                    .when().get(nextUrl).then().statusCode(200).contentType(ContentType.JSON) // validating response format is JSON
                    .extract().response();

            parsedResponse = response.as(StarWarsPojo.class);
            //2. Count characters from next page
            actualTotalCharactersCount += parsedResponse.getResults().size();
            //3. Get next page URL
            nextUrl = parsedResponse.getNext();

        }
        Assert.assertEquals(parsedResponse.getCount(),actualTotalCharactersCount); // validating count equals to total number of characters

    }


}

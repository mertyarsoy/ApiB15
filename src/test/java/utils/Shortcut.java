package utils;

import io.restassured.RestAssured;

public class Shortcut {

    public static void getAPICode(String url,int statusCode){
        //https://swapi.dev/api/people
        RestAssured.given().when().get(url).then().statusCode(statusCode).log().body();
    }
}

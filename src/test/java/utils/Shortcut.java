package utils;

import io.restassured.RestAssured;


public class Shortcut {

    public static void getAPICode(String path,int statusCode){
        //https://swapi.dev/api/people
        RestAssured.given().when().get(path).then().statusCode(statusCode).log().body();
    }

    public static void getAPICodeByHeader(String headerName,String headerValue,String limit,String objects,String path,int statusCode){
        RestAssured.given().header(headerName,headerValue).queryParam(limit,objects).when().get(path).then().statusCode(statusCode).extract().response();
    }
}

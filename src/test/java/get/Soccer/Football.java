package get.Soccer;

import groovyjarjarantlr4.v4.runtime.misc.ObjectEqualityComparator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Football {
    @Before
    public void setup() {
        // http://api.football-data.org/v2/competitions
        RestAssured.baseURI = "http://api.football-data.org/";
        RestAssured.basePath = "v2/competitions";
    }

    @Test
    public void competitionGetTest() {
        RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("count", Matchers.equalTo(168))
                .and()
                .body("competitions[0].name", Matchers.equalTo("AFC Champions League"));
    }

    @Test
    public void competitionSearchTest() {
        // GET http://api.football-data.org/v2/competitions
        // Parse the response
        // Search for MLS competition
        // validation that MLS competition id =2145

        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
//                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200).extract().response().jsonPath();

        for (int i = 0; i < jsonPath.getList("competitions").size(); i++) {
            if (jsonPath.getList("competitions.name").get(i).equals("MLS")) {
                System.out.println(jsonPath.getList("competitions.id").get(i));
                Assert.assertEquals(2145, jsonPath.getList("competitions.id").get(i));
            }
        }
    }

    @Test
    public void advancedRestAssuredTest() {
        ////////////// Advanced Rest Assured ///////////////////
        Response response = RestAssured.given().accept(ContentType.JSON)
//                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.find{ it.name=='MLS' }.id", Matchers.equalTo(2145)) // We can use "find" if it is List of Objects
                .extract().response();
    }

    @Test
    public void advancedRestAssuredTest2() {
        Response response = RestAssured.given().accept(ContentType.JSON)
//                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.collect {it.name}", Matchers.hasItem("Supercopa Argentina"))
                .extract().response();

        List<String> result = response.path("competitions.collect {it.name} ");
        System.out.println(result);
        Assert.assertEquals(168,result.size());

        // Get all country names where competition id is greater than 2006
        List <String> allCountryNames = response.path("competitions.findAll { it.id > 2006 }.area.name");
        System.out.println(allCountryNames);
        Assert.assertEquals(161,allCountryNames.size());

        // Sum all id values for all competitions
        int sumofIdValues = response.path("competitions.collect {it.id}.sum()");
        System.out.println(sumofIdValues);
    }
}


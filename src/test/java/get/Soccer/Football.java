package get.Soccer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import utils.ConfigReader;

public class Football {
    @Before
    public void setup(){
        // http://api.football-data.org/v2/competitions
        RestAssured.baseURI = "http://api.football-data.org/";
        RestAssured.basePath = "v2/competitions";
    }
    @Test
    public void competitionGetTest(){
        RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("count",Matchers.equalTo(168))
                .and()
                .body("competitions[0].name",Matchers.equalTo("AFC Champions League"));

    }
}

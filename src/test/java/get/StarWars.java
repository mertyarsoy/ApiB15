package get;

import io.restassured.RestAssured;
import org.junit.Test;
import utils.Shortcut;

public class StarWars {

    /*
    1.Define/determined the endpoint
    2.Added query String parameters as needed
    3.Defined HTTP Method
    4.Send
    5.Validate status code
     */

    @Test
    public void getSWChars(){
//        //https://swapi.dev/api/people
//        RestAssured.given().when().get("https://swapi.dev/api/people").then().statusCode(200).log().body();
        Shortcut.getAPICode("https://swapi.dev/api/people",200);

    }
}

package post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import pojo.Swagger.PetStorePojo;
import utils.PayloadUtils;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;

public class Serialization {
    @Test
    public void serializationTest() throws IOException {
        PetStorePojo pet = new PetStorePojo();

        pet.setId(78133);
        pet.setName("Hutch");
        pet.setStatus("Serving");

        File jsonFile = new File("src/test/resources/pet.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(jsonFile, pet);
    }

    @Test
    public void serializationTest2() {
        RestAssured.baseURI = "https://petstore.swagger.io/";
        RestAssured.basePath = "v2/pet";

        File jsonFile = new File("src/test/resources/pet.json");
        Response response = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(jsonFile)
                .when().post()
                .then().statusCode(200)
                .and().body("name", Matchers.is("Hutch"))
                .body("name",Matchers.equalTo("Hutch")).extract().response();

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals("Hutch",jsonPath.getString("name"));

        System.out.println(jsonPath.getString("name"));


    }

    @Test
    public void serializationTest3(){
        RestAssured.baseURI = "https://petstore.swagger.io/";
        RestAssured.basePath = "v2/pet";

        PetStorePojo pet = new PetStorePojo();
        pet.setId(8907);
        pet.setName("Zeus");
        pet.setStatus("playing");

        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(pet)
                .when().post()
                .then().statusCode(200)
                .body("name",Matchers.is("Zeus"))
                .body("status",Matchers.equalTo("playing")).extract().response().jsonPath();


        System.out.println(jsonPath.getString("id"));
        System.out.println(jsonPath.getString("name"));
        System.out.println(jsonPath.getString("status"));



    }
}

package put.Swagger;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.Swagger.PetStorePojo;
import utils.PayloadUtils;

public class PetStore {
    /*
     1. POST call to create a pet
     2. Deserialize and validate POST response
     3. PUT call to update an existing pet
     4. Deserialize and validate PUT response
     5. GET call to search for our pet
     6. Deserialize and validate GET response
     */

    @Test
    public void updatePetTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
        int petId = 786238; String petName = "Zeus", petStatus = "playing";

        RequestSpecification reqSpec = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);

        // 1. POST call to create a pet
        Response response = reqSpec
                .body(PayloadUtils.getPetPayload(petId,petName,petStatus))
                .when().post()
                .then().statusCode(200).extract().response();
        // 2. Deserialize and validate POST response
        PetStorePojo parsedResponse = response.as(PetStorePojo.class);

        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(petStatus,parsedResponse.getStatus());

        // 3. PUT call to update an existing pet
        String newStatus = "sleeping";
        response = reqSpec
                .body(PayloadUtils.getPetPayload(petId,petName,newStatus))
                .when().put()
                .then().statusCode(200).extract().response();
        //  4. Deserialize and validate PUT response
        parsedResponse = response.as(PetStorePojo.class);

        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(newStatus,parsedResponse.getStatus());

        // POST URL https://petstore.swagger.io/v2/pet
        // GET URL https://petstore.swagger.io/v2/pet/{petId}
        // 5. GET call to search for our pet
        response = reqSpec
                .when().get(String.valueOf(petId))
                .then().statusCode(200).extract().response();

        //  6. Deserialize and validate GET response
        parsedResponse = response.as(PetStorePojo.class);

        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(newStatus,parsedResponse.getStatus());
    }
}

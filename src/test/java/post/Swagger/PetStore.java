package post.Swagger;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Swagger.PetStorePojo;
import utils.PayloadUtils;

public class PetStore {
    @Test
    public void createPetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        //POST https://petstore.swagger.io/v2/pet
        //GET https://petstore.swagger.io/v2/pet//252177

        /*
        1. Created a pet
        2. Validated POST call response body and status code
        3. Sent GET request with newly created petId
        4. Validated GET call response body and status code
         */

        String petName = "diablo",petStatus = "unavailable"; long petId = 252177;

        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON) // bc it's post request
                .body(PayloadUtils.getPetPayload(252177,"diablo","unavailable"))
                .when()
                .post()
                .then()
                .statusCode(200).extract().response();

        PetStorePojo parsedResponse = response.as(PetStorePojo.class);

        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(petStatus,parsedResponse.getStatus());

        Response response2 = RestAssured.given().accept(ContentType.JSON)
                .when()
                .get("https://petstore.swagger.io/v2/pet/"+petId)
                .then()
                .statusCode(200).extract().response();

        PetStorePojo parsedGetResponse = response2.as(PetStorePojo.class);

        Assert.assertEquals(petId,parsedGetResponse.getId());
        Assert.assertEquals(petName,parsedGetResponse.getName());
        Assert.assertEquals(petStatus,parsedGetResponse.getStatus());
    }
}

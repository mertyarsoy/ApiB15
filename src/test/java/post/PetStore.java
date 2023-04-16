package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Pet.PetPojo;
import utils.PayloadUtils;

public class PetStore {
    @Test
    public void createPetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        String petName = "diablo",petStatus = "unavailable"; long petId = 252177;

        Response response = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON) // bc it's post request
                .body(PayloadUtils.getPetPayload(252177,"diablo","unavailable")).when().post().then().statusCode(200).extract().response();

        PetPojo parsedResponse = response.as(PetPojo.class);

        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(petStatus,parsedResponse.getStatus());

    }
}

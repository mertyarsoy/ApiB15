package delete.Swagger;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class PetStore {
    @Test
    public void deletePetTest() {
        put.Swagger.PetStore petStore = new put.Swagger.PetStore();
        petStore.updatePetTest();

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet/786238";
        int petId = 786238;

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().delete()
                .then().statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();
        String responseMessage = jsonPath.getString("message");
        Assert.assertEquals("786238",responseMessage);

    }
}

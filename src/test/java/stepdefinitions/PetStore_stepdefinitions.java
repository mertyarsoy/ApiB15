package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import pojo.Swagger.PetStorePojo;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.List;

public class PetStore_stepdefinitions {
    Response response;
    @Given("user has petstore endpoint")
    public void user_has_petstore_endpoint() {
        RestAssured.baseURI = ConfigReader.readProperty("baseURI");
        RestAssured.basePath = ConfigReader.readProperty("basePath");

    }
    @When("user sends GET request to list pets")
    public void user_sends_get_request_to_list_pets() {
       response = RestAssured.given().accept(ContentType.JSON).queryParam("status","sold")
               .when().get("findByStatus").then().extract().response();
    }
    @Then("status code is {int}")
    public void status_code_is(int expectedStatusCode) {
        response.prettyPrint(); // prints response body
        Assert.assertEquals(expectedStatusCode,response.getStatusCode());
    }
    @Then("response contains list of pets")
    public void response_contains_list_of_pets() {
        List<PetStorePojo> petStorePojoList = new ArrayList<>();
        petStorePojoList = response.as(petStorePojoList.getClass());
        Assert.assertTrue(petStorePojoList.size() > 0);
    }

    @When("^user sends (non-existing|invalid) pet by id GET request$")
    public void user_sends_non_existing_pet_by_id_get_request(String petIdType) {
        String petId;
        if (petIdType.equalsIgnoreCase("non-existing")){
            petId = "5233523";
        }else {
            petId = "52a3da35a23";
        }
        response = RestAssured.given().accept(ContentType.JSON)
                .when().get(petId);
    }
    @Then("error message is {string}")
    public void error_message_is(String ErrorMessage) {
        response.then().body("message", Matchers.is(ErrorMessage));
    }

}

package post.Slack;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Slack.SlackPojo;
import utils.ConfigReader;
import utils.PayloadUtils;

public class SlackApp {
    @Test
    public void sendSlackMessageTest() {
        //https://slack.com/api/chat.postMessage
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        Response response = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.readProperty("slackToken"))
                .body(PayloadUtils.getSlackMessagePayload("Mert: hello channel,from POSTMAN"))
                .when()
                .post()
                .then()
                .statusCode(200).extract().response();

        /*
        Slack task:
	- Deserialize response with POJO
	- Validate "ok" field
	- Validate message.text field
         */
        SlackPojo parsedResponse = response.as(SlackPojo.class);
        JsonPath jsonPath = response.jsonPath();

        Assert.assertTrue("Test Passed", parsedResponse.isOk());
        Assert.assertEquals("Test Passed", "Mert: hello channel,from POSTMAN", parsedResponse.getMessage().getText());


        boolean ismessage = jsonPath.getBoolean("ok");
        Assert.assertTrue(ismessage);

        String text = jsonPath.getString("message.text");
        Assert.assertEquals("Mert: hello channel,from POSTMAN", text);

        String type = jsonPath.getString("message.blocks.elements.type");
        Assert.assertEquals("[[rich_text_section]]", type);

        System.out.println("ok: " + ismessage);
        System.out.println("text: " + text);
        System.out.println("type: " + type);
    }

}

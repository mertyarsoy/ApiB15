package post.Slack;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import pojo.Slack.SlackMessagePojo;
import pojo.Slack.SlackPojo;
import utils.ConfigReader;
import utils.PayloadUtils;

import java.io.File;
import java.io.IOException;

public class SlackSerialization {
    @Test
    public void creatingSlackFile() throws IOException {
        SlackPojo slack = new SlackPojo();

        slack.setOk(true);
        slack.setChannel("C052ZQBE39D");
        slack.setTs("1681871220.486039");

        File jsonFile = new File("src/test/resources/slack.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(jsonFile, slack);
    }

    @Test
    public void slackSerializationTest() {
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        //File jsonFile = new File("src/test/resources/slack.json");
        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.readProperty("slackToken"))
                .body(PayloadUtils.getSlackMessagePayload("Homework"))
                .when().post()
                .then().statusCode(200)
                .and().body("channel", Matchers.equalTo("C052ZQBE39D")).extract().response().jsonPath();

        Assert.assertTrue("Passed", jsonPath.getBoolean("ok"));
        Assert.assertEquals("Passed", "C052ZQBE39D", jsonPath.getString("channel"));

        System.out.println(jsonPath.getString("message.blocks.block_id"));
    }



    

}

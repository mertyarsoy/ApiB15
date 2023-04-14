package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Test;
import utils.Shortcut;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReqRes {

    //Deserialization Json -> Java
    //Serialization Java -> Json
    @Test
    public void ReqRes() {
        //Shortcut.getAPICodeByHeader("Accept","application/json","https://reqres.in/api/users?page=2",200);
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Map<String, Object> deserializedResponse = response.as(new TypeRef<>() {
        });

        System.out.println(deserializedResponse.get("per_page"));
        System.out.println(deserializedResponse.get("total"));

        List <Map<String,Object>> dataList = (List<Map<String, Object>>) deserializedResponse.get("data");
        System.out.println(dataList);
//
//        for (int i = 0; i < dataList.size(); i++) {
//           System.out.println(dataList.get(i).get("email"));
//        }

        Map<String, Object> supportMap = (Map<String, Object>) deserializedResponse.get("support");
        Set<Map.Entry<String, Object>> supports = supportMap.entrySet();
        for (Map.Entry<String, Object> support : supports) {
            System.out.println("Pair: " + support);
            System.out.println("Key: " + support.getKey());
            System.out.println("Value: " + support.getValue());
        }


    }

}

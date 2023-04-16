package get;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.AbilityPojo;
import pojo.AbilityResultPojo;
import pojo.PokemonPojo;
import pojo.PokemonResultPojo;

import java.util.List;
import java.util.Map;

public class Pokemon {

    /*
    1. GET https://pokeapi.co/api/v2/pokemon?limit=100
2. Deserialize response using POJO classes
3. Validate count = 1279
4. Find url for pikachu
5. Validate that we got 100 Pokemon
     */
    @Test
    public void PetStore() {
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .queryParam("limit", "100")
                .when()
                .get("https://pokeapi.co/api/v2/pokemon")
                .then()
                .statusCode(200)
                .extract()
                .response();

        PokemonPojo deserializedResp = response.as(PokemonPojo.class);
        Assert.assertEquals(1281, deserializedResp.getCount());

        List<PokemonResultPojo> results = deserializedResp.getResults();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getName().equals("pikachu")) {
                System.out.println(results.get(i).getName());
                System.out.println(results.get(i).getUrl());
            }
            Assert.assertEquals(100, results.size());
        }
    }
    /*
    - get https://pokeapi.co/api/v2/pokemon
    - validate you got 20 pokemons
    - get every pokemons ability and store those in Map<String, List<String>>
     */

    @Test
    public void getPokemonAndAbilities() {}

    }





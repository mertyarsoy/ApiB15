package get.Pokemon;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Pokemon.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    HOMEWORK:
    - get https://pokeapi.co/api/v2/pokemon
    - validate you got 20 pokemons
    - get every pokemons ability and store those in Map<String, List<String>>
     */
    @Test
    public void getPokemonAbilities() {
        RestAssured.baseURI = "https://pokeapi.co/api/v2/pokemon";
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200).extract().response();

        PokemonPojo parsedResponse = response.as(PokemonPojo.class);
        List<PokemonResultPojo> pokemons = parsedResponse.getResults();
        Assert.assertEquals(20, pokemons.size());

        Map<String, List<String>> res = new LinkedHashMap<>();
        for (int i = 0; i < pokemons.size(); i++) {
            Response response2 = RestAssured.given()
                    .accept(ContentType.JSON)
                    .when()
                    .get(pokemons.get(i).getUrl())
                    .then()
                    .statusCode(200).extract().response();

            EachPokemonPojo parsedResponse2 = response2.as(EachPokemonPojo.class);
            List<PokemonAbilitesPojo> abilities = parsedResponse2.getAbilities();
            List<String> eachAbility = new ArrayList<>();
            String pokemon = parsedResponse.getResults().get(i).getName();
            for (int j = 0; j < abilities.size(); j++) {
                eachAbility.add(parsedResponse2.getAbilities().get(j).getAbility().getName());
            }
            res.put(pokemon, eachAbility);
            for (int j = 0; j < eachAbility.size(); j++) {
                System.out.println(pokemon);
                System.out.println(eachAbility.get(j));
            }
        }
        System.out.println(res);
    }

//        String dynamicLink = "https://pokeapi.co/api/v2/pokemon";
//        for (int i = 1; i <= 100; i++) {
//            dynamicLink = dynamicLink.concat(String.valueOf(i));
//            Response response = RestAssured.given()
//                    .header("Accept", "application/json")
//                    .queryParam("limit", "100")
//                    .when()
//                    .get("https://pokeapi.co/api/v2/pokemon")
//                    .then()
//                    .statusCode(200)
//                    .extract()
//                    .response();
//
//            PokemonPojo deserializedResp = response.as(PokemonPojo.class);
//            List<PokemonResultPojo> results = deserializedResp.getResults();
//            for (int j = 0; j < results.size(); j++) {
//                System.out.println(results.get(j).getName());
//
//            }
//        }

}
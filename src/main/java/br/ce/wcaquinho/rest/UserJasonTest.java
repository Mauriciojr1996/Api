package br.ce.wcaquinho.rest;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJasonTest {

	@Test

	public void deveVerificarPrimeiroNivel() {

//		{
//		  "id": 1,
//		  "name": "João da Silva",
//		  "age": 30,
//		  "salary": 1234.5678
//		}

		RestAssured.given().when() // primeiro teste com api pegando informaçoes do site
				.get("https://restapi.wcaquino.me/users/1") // e validando as informaçãoes corretas !!!

				.then().statusCode(200).body("id", Matchers.is(1)).body("name", Matchers.containsString("Silva"))
				.body("age", Matchers.greaterThan(18));

	}

	@Test

	public void deveVerificarPrimeiroNivelOutrasformas() {

		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");

		// path
		System.out.println(response.path("id")); // conseguimos ver o resultado na tela
		Assert.assertEquals(new Integer(1), response.path("id")); // neste caso comparamos os dois objetos do site

		// jasonpath

		JsonPath jpath = new JsonPath(response.asString()); // forma diferente de fazer esssa eu gostei mais

		Assert.assertEquals(1, jpath.getInt("id"));

		// from

		int id = JsonPath.from(response.asString()).getInt("id"); // forma diferente de fazer
		Assert.assertEquals(1, id);

	}

	@Test

	public void deveVerificarsegundonivel() {

//		{
//			  "id": 2,
//			  "name": "Maria Joaquina",
//			  "endereco": {
//			    "rua": "Rua dos bobos",
//			    "numero": 0
//			  },
//			  "age": 25,
//			  "salary": 2500
//			}

		RestAssured.given().when().get("https://restapi.wcaquino.me/users/2") // nivel doi conseguimos fazer analise de
																				// mais de uma infomação

				.then().statusCode(200).body("id", Matchers.is(2)).body("name", Matchers.containsString("Joaquina"))
				.body("endereco.rua", Matchers.containsString("Rua dos bobos")); // aqui por exemplo entramos em
																					// endereço e rua !!

	}

	@Test

	public void deveVerificarumalista() {

//		{
//			  "id": 3,
//			  "name": "Ana Júlia",
//			  "age": 20,
//			  "filhos": [
//			    {
//			      "name": "Zezinho"
//			    },
//			    {
//			      "name": "Luizinho"
//			    }
//			  ]
//			}

		RestAssured.given().when().get("https://restapi.wcaquino.me/users/3") // nivel doi conseguimos fazer analise de
																				// mais de uma infomação

				.then().statusCode(200).body("id", Matchers.is(3)) // CONTEM O ID 3 ?
				.body("name", Matchers.containsString("Ana")) // CONTEM AS STRINGS "ANA" ?
				.body("filhos", Matchers.hasSize(2)) // CONTEM 2 LISTAS ?
				.body("age", Matchers.is(20)).body("filhos[0].name", Matchers.is("Zezinho")) // verificando se nesta
																								// posição de 0 esta o
																								// nome zezinho
				.body("filhos[1].name", Matchers.is("Luizinho"))// verificando se nesta posição de 1 esta o nome
																// luizinho
				.body("filhos.name", Matchers.hasItem("Zezinho"))// verificando se tem esse item
				.body("filhos.name", Matchers.hasItems("Zezinho", "Luizinho"))// verificando se tem esses items
		;
	}

	@Test

	public void deveinformaroerrodemenssagem() {

//		{
//			  "error": "Usuário inexistente"
//			}

		RestAssured.given()
		            .when()
		                  .get("https://restapi.wcaquino.me/users/4") // nivel doi conseguimos fazer analise de
																				// mais de uma infomação

	                .then()
		                  .statusCode(404)
		                  .body("error", Matchers.is("Usuário inexistente"))

		;
	}
	
	@SuppressWarnings("unchecked")
	@Test
	
	public void deveverificarListaRaiz() {
		
//		[
//		  {
//		    "id": 1,
//		    "name": "João da Silva",
//		    "age": 30,
//		    "salary": 1234.5678
//		  },
//		  {
//		    "id": 2,
//		    "name": "Maria Joaquina",
//		    "endereco": {
//		      "rua": "Rua dos bobos",
//		      "numero": 0
//		    },
//		    "age": 25,
//		    "salary": 2500
//		  },
//		  {
//		    "id": 3,
//		    "name": "Ana Júlia",
//		    "age": 20,
//		    "filhos": [
//		      {
//		        "name": "Zezinho"
//		      },
//		      {
//		        "name": "Luizinho"
//		      }
//		    ]
//		  }
//		]
//		
		
		RestAssured.given()
        .when()
              .get("https://restapi.wcaquino.me/users") // verificando para ver se tem 3 chaves

        .then()
              .statusCode(200)
              .body("$", Matchers.hasSize(3))
              .body("name", Matchers.hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
              .body("age[1]", Matchers.is(25))
              .body("filhos.name", Matchers.hasItem(Arrays.asList("Zezinho" ,"Luizinho" ))) 
              .body("salary", Matchers.contains(1234.5678f,2500 , null))
       ;		
		
	}
	
	
	
	
	
	

}

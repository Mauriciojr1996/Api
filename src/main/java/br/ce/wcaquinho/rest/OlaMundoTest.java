package br.ce.wcaquinho.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.*;
import org.hamcrest.collection.HasItemInArray;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringStartsWith;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!")); // aqui foi imprimido o resultado da
																				// string exibida no site
		Assert.assertTrue(response.statusCode() == 200); // aqui foi exibido o codigo do response do site
		Assert.assertTrue("O status code deveria ser 200 " ,response.statusCode() == 200);
		Assert.assertEquals(200 , response.statusCode()); // verificando se o status bate com a informação correta 
		
		ValidatableResponse validacao = response.then(); 
		validacao.statusCode(200); // aqui usamos uma maneira de validação para achar onde esta o erro

	}
	@Test
	
	public void devoConhecerOutrasFoormasDeRestAssured() {
		
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validacao = response.then(); 
		validacao.statusCode(200); 
		
		RestAssured.get("http://restapi.wcaquino.me/ola").then().statusCode(200);

		
		RestAssured.given() // dado  // pré condiçoes
		
		.when() // quando // ação
		.get("http://restapi.wcaquino.me/ola")
		
		
		.then()// entao // Assertivas
		.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		
		Assert.assertThat("maria", Matchers.is("maria")); // vemos se esta igual os rsultados dessa forma
		Assert.assertThat(128, Matchers.is(128));// comparando numero inteiro se sao iguais
		Assert.assertThat(128, Matchers.isA(Integer.class));// comparando se o tipo 128 e o mesmo de integer.class
		Assert.assertThat(128, Matchers.greaterThan(127)); // 128 é maior que 127 ?? ok
		Assert.assertThat(128, Matchers.lessThan(130));// 128 é menor que 130 ? ok 
		
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		Assert.assertThat(impares, Matchers.hasSize(5)); // a quantidade de posiçoes esta correta ?
		Assert.assertThat(impares, Matchers.contains(1,3,5,7,9));// contem esses items na variavel impar? ok
		Assert.assertThat(impares, Matchers.containsInAnyOrder(1,5,3,9,7));// contem os item do impar porem fora de orden? ok
		Assert.assertThat(impares, Matchers.hasItem(1));// verificando se o item 1 esta na posiçoes do impar ? ok
		
		Assert.assertThat("Maria", Matchers.not("joao")); // maria é diferente de joao ? ok
		
		
	}
	
	@Test
	public void devoValidarBody() {
		 
        RestAssured.given() 
		
		.when() 
		.get("http://restapi.wcaquino.me/ola")
		
		
		.then()
		
		.statusCode(200)
		.body( Matchers.is("Ola Mundo!"))
		.body(Matchers.containsString("Mundo"));
		
        
	
	}

}

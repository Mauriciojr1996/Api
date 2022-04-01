package br.ce.wcaquinho.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundo {

	public static void main(String[] args) {

		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		System.out.println(response.getBody().asString().equals("Ola Mundo!")); // aqui foi imprimido o resultado da string exibida no site
		System.out.println(response.statusCode() == 200); // aqui foi exibido o codigo do response do site

		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);  // aqui usamos uma maneira de validação  para achar onde esta o erro
		
	}

}

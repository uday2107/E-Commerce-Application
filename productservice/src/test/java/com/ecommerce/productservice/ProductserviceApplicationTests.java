package com.ecommerce.productservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductserviceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private int port;

	static {
		mongoDBContainer.start();
	}

	@BeforeEach
	public void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}

	@Test
	void CreateProductTest() {

		String request =
				"""
						{
						    "name" : "Macbook Air M2",
						    "description" : "This is Macbook Air M2 with 8 & 256 GB specification",
						    "price" : 180000
						}
												
						""";

		RestAssured.given()
				.contentType("application/json")
				.body(request)
				.when()
				.post("/api/product/add")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Macbook Air M2"))
				.body("price",Matchers.equalTo(180000));
	}

}

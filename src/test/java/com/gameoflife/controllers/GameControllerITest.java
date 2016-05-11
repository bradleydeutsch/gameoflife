package com.gameoflife.controllers;

import com.gameoflife.Application;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class GameControllerITest {

	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void before() {

		RestAssured.port = serverPort;
	}

	@Test
	public void startNewGameReturnsNewGameState() {

		String body =
				given()
						.get("/game")
						.then()
							.statusCode(is(HttpStatus.OK.value()))
							.extract().body().asString();

		assertThat(body).isNotNull();
	}

	@Test
	public void makeGamePassWillUpdateAllCellsAndReturnTheLatestStateOfTheBoard() {

		given().get("/game");

		String body =
				given()
						.get("/game/pass")
						.then()
						.statusCode(is(HttpStatus.OK.value()))
						.extract().body().asString();

		assertThat(body).isNotNull();
	}
}

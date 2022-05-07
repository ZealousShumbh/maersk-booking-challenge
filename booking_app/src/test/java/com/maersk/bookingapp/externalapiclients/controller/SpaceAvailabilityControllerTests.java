package com.maersk.bookingapp.externalapiclients.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.WireMockServer;

public class SpaceAvailabilityControllerTests {
	private static Logger logger = LoggerFactory.getLogger(SpaceAvailabilityControllerTests.class);

	private RestTemplate restTemplate;
	private WireMockServer wireMockServer;

	@BeforeEach
	void configureSystemUnderTest() {
		this.restTemplate = new RestTemplate();
		this.wireMockServer = new WireMockServer(options().bindAddress("localhost").port(9090));
		this.wireMockServer.start();
		configureFor("localhost", 9090);
		logger.info("wireMockServer" + wireMockServer);
	}

	@Test
	void shouldEnsureThatServerWasStarted() {
		givenThat(get(urlEqualTo("/")).willReturn(aResponse().withStatus(200)));

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9090", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void shouldReturnOkResponseForJson() {
		givenThat(get(urlEqualTo("/api/message")).willReturn(okJson("{ \"availableSpace\": 1 }")));

		String serverUrl = buildApiMethodUrl();
		ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(response.getBody()).isEqualTo("{ \"message\": \"Hello World!\" }");
	}

	private String buildApiMethodUrl() {
		return String.format("http://localhost:%d/api/message", 9090);
	}

	@AfterEach
	void stopWireMockServer() {
		this.wireMockServer.stop();
	}
}

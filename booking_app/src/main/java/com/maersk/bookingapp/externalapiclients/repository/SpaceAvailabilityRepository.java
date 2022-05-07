package com.maersk.bookingapp.externalapiclients.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@Component
public class SpaceAvailabilityRepository {

	private final String url;
	private final int port;

	private final RestTemplate restTemplate;

	private SpaceAvailabilityRepository(RestTemplate restTemplate,
			@Value("${app.external.url.space.availability}") String url,
			@Value("${app.external.url.space.availability.port}") int port) {
		this.url = url;
		this.restTemplate = restTemplate;
		this.port = port;
	}

	@PostConstruct
	void initialize() {
		WireMockServer wireMockServer = new WireMockServer(
				WireMockConfiguration.wireMockConfig().port(this.port).usingFilesUnderClasspath("wiremock"));
		wireMockServer.start();
	}

	public String getAvailableSpace() {
		return restTemplate.getForObject(url, String.class);
	}
}

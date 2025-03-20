package com.mehmetozanguven.outbox_pattern_with_spring_boot;

import com.mehmetozanguven.outbox_pattern_with_spring_boot.clearDatabase.ClearDatabaseBeforeEach;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.order.CreateOrderRequest;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.testcontainer.EnableTestcontainers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableTestcontainers
@ClearDatabaseBeforeEach
class OutboxPatternWithSpringBootApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;
	@LocalServerPort
	int randomServerPort;

	public String getBaseUrl() {
		return "http://localhost:" + randomServerPort;
	}

	public URI generateUri(String endpoint) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getBaseUrl() + endpoint);
		return builder.build().toUri();
	}

	@Test
	void testOrderCreation() {
		ResponseEntity<String> orderResponse =
				testRestTemplate.postForEntity(generateUri("/api/order"), CreateOrderRequest.builder().productName("test").build(), String.class);

		Assertions.assertEquals("created", orderResponse.getBody());

	}

}

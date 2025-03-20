package com.mehmetozanguven.outbox_pattern_with_spring_boot;

import com.mehmetozanguven.outbox_pattern_with_spring_boot.clearDatabase.ClearDatabaseBeforeEach;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.order.CreateOrderRequest;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.order.OrderRepository;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.testcontainer.EnableTestcontainers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableTestcontainers
@ClearDatabaseBeforeEach
public class KafkaFailureTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @MockitoBean
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    OrderRepository orderRepository;
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
    void testKafkaFailure() {
        Mockito.when(kafkaTemplate.send(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("Failed"));
        ResponseEntity<String> orderResponse =
                testRestTemplate.postForEntity(generateUri("/api/order"), CreateOrderRequest.builder().productName("test").build(), String.class);

        // kafka operation fails
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, orderResponse.getStatusCode());
        // but we saved order in our DB !!!
        Assertions.assertEquals(1, orderRepository.count());
    }
}

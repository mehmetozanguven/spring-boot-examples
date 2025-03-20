package com.mehmetozanguven.outbox_pattern_with_spring_boot.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.outbox.OutboxEntity;
import com.mehmetozanguven.outbox_pattern_with_spring_boot.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public String createOrderWithOutbox(CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        OrderEntity createdEntity = orderRepository.save(OrderEntity.builder().productName(createOrderRequest.getProductName()).build());
        String data = objectMapper.writeValueAsString(OrderEventCreated.builder().id(createdEntity.getId()).build());
        outboxRepository.save(OutboxEntity.builder().type(OrderEventCreated.class.getSimpleName())
                        .content(data)
                        .generatedUtc(OffsetDateTime.now())
                .build());
        return "created";
    }

    public String createOrder(CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        OrderEntity createdEntity = orderRepository.save(OrderEntity.builder().productName(createOrderRequest.getProductName()).build());

        String data = objectMapper.writeValueAsString(OrderEventCreated.builder().id(createdEntity.getId()).build());
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orderCreated", data);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send it to the kafka: {}", result.getProducerRecord());
            }
        });
        return "created";
    }
}

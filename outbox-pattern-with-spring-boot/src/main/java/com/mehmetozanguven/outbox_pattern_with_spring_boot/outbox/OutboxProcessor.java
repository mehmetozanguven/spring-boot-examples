package com.mehmetozanguven.outbox_pattern_with_spring_boot.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void processOutboxMessages() {
        final int batchSize = 100;
        Pageable pageRequest = PageRequest.of(0, batchSize, Sort.by(Sort.Direction.ASC, "generatedUtc"));
        Slice<OutboxEntity> unProcessedMessages = outboxRepository.getUnProcessedMessages(pageRequest);
        unProcessedMessages.forEach(outboxEntity -> {
            // we may also convert the content to the given type and push it into kafka !!
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("orderCreated", outboxEntity.getContent());
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Message send it to the kafka: {}", result.getProducerRecord());
                    // call another service to update outboxMessage as processed, update entity.consumedUtc time
                } else {
                    log.info("Something went wrong while processing message: {}", result.getRecordMetadata().topic());
                }
            });
        });
    }
}

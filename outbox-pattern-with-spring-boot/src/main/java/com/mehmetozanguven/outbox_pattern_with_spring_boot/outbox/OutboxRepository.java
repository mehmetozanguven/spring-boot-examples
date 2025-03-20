package com.mehmetozanguven.outbox_pattern_with_spring_boot.outbox;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEntity, String> {

    @Query("FROM OutboxEntity qe WHERE qe.consumedUtc is NULL")
    Slice<OutboxEntity> getUnProcessedMessages(Pageable pageable);
}

package com.mehmetozanguven.outbox_pattern_with_spring_boot.order;

import lombok.Builder;

@Builder
public record OrderEventCreated(
        String id
) {
}

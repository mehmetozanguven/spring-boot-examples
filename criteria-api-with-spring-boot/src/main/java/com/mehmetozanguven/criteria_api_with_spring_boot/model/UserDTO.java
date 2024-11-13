package com.mehmetozanguven.criteria_api_with_spring_boot.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record UserDTO(
        Long id,
        String email,
        String name,
        OffsetDateTime createDate
) {
}

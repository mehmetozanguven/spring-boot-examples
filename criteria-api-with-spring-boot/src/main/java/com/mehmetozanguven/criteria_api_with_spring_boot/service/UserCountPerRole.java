package com.mehmetozanguven.criteria_api_with_spring_boot.service;

import lombok.Builder;

@Builder
public record UserCountPerRole(
        String role,
        Long count
) {
}

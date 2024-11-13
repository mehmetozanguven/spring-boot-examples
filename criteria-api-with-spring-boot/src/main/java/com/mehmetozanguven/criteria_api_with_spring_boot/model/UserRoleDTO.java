package com.mehmetozanguven.criteria_api_with_spring_boot.model;

import lombok.Builder;

@Builder
public record UserRoleDTO(
        Long id,
        String role
) {
}

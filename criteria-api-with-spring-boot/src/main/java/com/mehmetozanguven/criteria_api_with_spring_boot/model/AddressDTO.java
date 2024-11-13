package com.mehmetozanguven.criteria_api_with_spring_boot.model;

import lombok.Builder;

@Builder
public record AddressDTO(
        Long id,
        String city,
        String address
) {
}

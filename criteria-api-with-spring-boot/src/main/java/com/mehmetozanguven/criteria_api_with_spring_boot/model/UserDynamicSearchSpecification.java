package com.mehmetozanguven.criteria_api_with_spring_boot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Set;

@Builder
@Getter
public class UserDynamicSearchSpecification {
    @Singular
    private Set<String> emailLikes;
    @Singular
    private Set<String> roles;
    @Singular
    private Set<String> cities;
}

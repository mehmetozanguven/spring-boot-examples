package com.mehmetozanguven.criteria_api_with_spring_boot.util;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.*;
import com.mehmetozanguven.criteria_api_with_spring_boot.model.UserDynamicSearchSpecification;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserSpecification {
    private UserSpecification() {}

    public static Specification<User> createSpecification(UserDynamicSearchSpecification dynamicSearchSpecification) {
        return Specification.where(searchByEmail(dynamicSearchSpecification.getEmailLikes()))
                .and(searchByRoles(dynamicSearchSpecification.getRoles()))
                .and(searchByCities(dynamicSearchSpecification.getCities()));
    }

    public static Specification<User> searchByEmail(Set<String> emails) {
        if (CollectionUtils.isEmpty(emails)) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            emails.forEach(email -> {
                Predicate predicate = criteriaBuilder.like(root.get(User_.email), "%" + email + "%");
                predicates.add(predicate);
            });

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<User> searchByRoles(Set<String> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            // avoid error for the count query
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                Fetch<User, UserRole> userRoleFetch = root.fetch(User_.userRoles, JoinType.LEFT);
                Join<User, UserRole> userRoleJoin = (Join<User, UserRole>) userRoleFetch;

                root.fetch(User_.userAddresses, JoinType.LEFT);
                return userRoleJoin.get(UserRole_.role).in(roles);
            } else {
                Join<User, UserRole> userRoleJoin = root.join(User_.userRoles, JoinType.LEFT);
                root.join(User_.userAddresses, JoinType.LEFT);
                return userRoleJoin.get(UserRole_.role).in(roles);
            }

        };
    }


    public static Specification<User> searchByCities(Set<String> cities) {
        if (CollectionUtils.isEmpty(cities)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {

            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                Fetch<User, UserRole> userRoleFetch = root.fetch(User_.userRoles, JoinType.LEFT);
                Join<User, UserRole> userRoleJoin = (Join<User, UserRole>) userRoleFetch;

                Fetch<User, Address> addressFetch = root.fetch(User_.userAddresses, JoinType.LEFT);
                Join<User, Address> addressJoin = (Join<User, Address>) addressFetch;
                return addressJoin.get(Address_.city).in(cities);
            } else {
                Fetch<User, UserRole> userRoleFetch = root.fetch(User_.userRoles, JoinType.LEFT);
                Join<User, UserRole> userRoleJoin = (Join<User, UserRole>) userRoleFetch;

                Fetch<User, Address> addressFetch = root.fetch(User_.userAddresses, JoinType.LEFT);
                Join<User, Address> addressJoin = (Join<User, Address>) addressFetch;
                return addressJoin.get(Address_.city).in(cities);
            }
        };
    }
}

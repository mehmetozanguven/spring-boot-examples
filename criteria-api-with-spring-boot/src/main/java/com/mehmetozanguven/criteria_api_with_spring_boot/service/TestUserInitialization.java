package com.mehmetozanguven.criteria_api_with_spring_boot.service;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.Address;
import com.mehmetozanguven.criteria_api_with_spring_boot.entity.User;
import com.mehmetozanguven.criteria_api_with_spring_boot.entity.UserRole;
import com.mehmetozanguven.criteria_api_with_spring_boot.repository.AddressRepository;
import com.mehmetozanguven.criteria_api_with_spring_boot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestUserInitialization {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void initializeTestUsers() {
        Set<User> users = newUsers().collect(Collectors.toSet());
        for (User each : users) {
            Set<Address> addresses = Objects.requireNonNullElse(each.getUserAddresses(), Set.of());
            for (Address eachAddress :addresses) {
                addressRepository.save(eachAddress);
            }
            userRepository.save(each);
        }

        log.info("Test users are saved into database");
    }

    // Four users:
    // one is admin, one has two roles, one has two addresses, one has no address, one has no address and role
    public static Stream<User> newUsers() {
        UserRole.UserRoleBuilder customer = UserRole.builder()
                .role("customer");
        UserRole.UserRoleBuilder admin = UserRole.builder()
                .role("admin");
        Address.AddressBuilder address = Address.builder()
                .address("Dummy Address İstanbul")
                .city("İstanbul");
        Address.AddressBuilder anotherAddress = Address.builder()
                .address("Dummy Address New York")
                .city("New York");

        return Stream.of(
                User.builder()
                        .email("test1@gmail.com")
                        .name("test1")
                        .userRoles(Set.of(
                                customer.build(),
                                admin.build()
                        ))
                        .userAddresses(Set.of(address.build()))
                        .build(),
                User.builder()
                        .email("test2@gmail.com")
                        .name("test2")
                        .userRoles(Set.of(
                                customer.build()
                        ))
                        .userAddresses(Set.of(address.build()))
                        .build(),
                User.builder()
                        .email("test3@gmail.com")
                        .name("test3")
                        .userRoles(Set.of(
                                customer.build()
                        ))
                        .userAddresses(Set.of(address.build(), anotherAddress.build()))
                        .build(),
                User.builder()
                        .email("test4@gmail.com")
                        .name("test4")
                        .build()
        );
    }
}

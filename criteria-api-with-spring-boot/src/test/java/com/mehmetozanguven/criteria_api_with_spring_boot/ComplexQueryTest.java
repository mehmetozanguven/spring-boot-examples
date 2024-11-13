package com.mehmetozanguven.criteria_api_with_spring_boot;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.User;
import com.mehmetozanguven.criteria_api_with_spring_boot.model.UserDynamicSearchSpecification;
import com.mehmetozanguven.criteria_api_with_spring_boot.service.TestUserInitialization;
import com.mehmetozanguven.criteria_api_with_spring_boot.service.UserCountPerRole;
import com.mehmetozanguven.criteria_api_with_spring_boot.model.UserDTO;
import com.mehmetozanguven.criteria_api_with_spring_boot.service.UserService;
import com.mehmetozanguven.criteria_api_with_spring_boot.testcontainer.EnableTestcontainers;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.function.Consumer;
import java.util.stream.Stream;

@EnableTestcontainers
@SpringBootTest
public class ComplexQueryTest {

    @Autowired
    private TestUserInitialization testUserInitialization;
    @Autowired
    private UserService userService;
    @Autowired
    private Flyway flyway;

    // Clear and migrate database for each test
    @BeforeEach
    public void restoreDatabase() {
        flyway.clean();
        flyway.migrate();
        testUserInitialization.initializeTestUsers();
    }

    @ParameterizedTest
    @MethodSource("paginationRequests")
    void testPaginationWithDTO(PageRequest pageRequest) {
        Long newUsers = TestUserInitialization.newUsers().count();
        Page<UserDTO> page = userService.getAllUserWithDTOProjection(pageRequest);
        Assertions.assertEquals(newUsers, page.getTotalElements());
        Assertions.assertNotNull(page.getContent());
        Assertions.assertEquals((int) (newUsers / pageRequest.getPageSize()), page.getTotalPages());

        UserDTO user = page.getContent().getFirst();
        Assertions.assertNotNull(user.email());
    }

    @ParameterizedTest
    @MethodSource("paginationRequests")
    void testPaginationWithJoin(PageRequest pageRequest) {
        Long newUsers = TestUserInitialization.newUsers().count();
        Page<User> page = userService.getAllUsersWithJoin(pageRequest);
        Assertions.assertEquals(newUsers, page.getTotalElements());
        Assertions.assertNotNull(page.getContent());
        Assertions.assertEquals((int) (newUsers / pageRequest.getPageSize()), page.getTotalPages());

        User user = page.getContent().getFirst();
        Assertions.assertNotNull(user.getUserRoles());
    }


    @ParameterizedTest
    @MethodSource("paginationRequests")
    void testPaginationWithoutJoin(PageRequest pageRequest) {
        Long newUsers = TestUserInitialization.newUsers().count();
        Page<User> page = userService.getAllUsersWithoutJoin(pageRequest);
        Assertions.assertEquals(newUsers, page.getTotalElements());
        Assertions.assertEquals((int) (newUsers / pageRequest.getPageSize()), page.getTotalPages());
    }

    @ParameterizedTest
    @MethodSource("paginationRequests")
    void countDistinctUserPerRole(PageRequest pageRequest) {
        Page<UserCountPerRole> page = userService.countDistinctUsersPerRole(pageRequest);
        // test users have two different roles
        Assertions.assertEquals(2, page.getTotalElements());
    }

    private UserDynamicSearchSpecification buildSpecification(Consumer<UserDynamicSearchSpecification.UserDynamicSearchSpecificationBuilder> customizer) {
        var builder = UserDynamicSearchSpecification.builder();
        customizer.accept(builder);
        return builder.build();
    }

    @Test
    void dynamicUserSearch() {
        // search by email like and role
        var searchSpecification = buildSpecification((builder) -> builder
                .emailLike("test1@gmail.com")
                .emailLike("test2@gmail.com")
                .role("customer")
        );
        Page<User> users = userService.findUserDynamically(searchSpecification, PageRequest.of(0, 1));
        Assertions.assertEquals(2, users.getTotalElements());
        User user = users.toList().getFirst();
        User finalUser = user;
        Assertions.assertAll(
                () -> Assertions.assertNotNull(finalUser.getEmail()),
                () -> Assertions.assertNotNull(finalUser.getUserRoles()),
                () -> Assertions.assertNotNull(finalUser.getUserAddresses())
        );

        // search only with roles
        searchSpecification = buildSpecification((builder) -> builder
                .role("customer")
        );
        users = userService.findUserDynamically(searchSpecification, PageRequest.of(0, 1));
        Assertions.assertEquals(3, users.getTotalElements());
        user = users.toList().getFirst();
        User finalUser1 = user;
        Assertions.assertAll(
                () -> Assertions.assertNotNull(finalUser1.getEmail()),
                () -> Assertions.assertNotNull(finalUser1.getUserRoles()),
                () -> Assertions.assertNotNull(finalUser1.getUserAddresses())
        );
    }

    static Stream<Arguments> paginationRequests() {
        return Stream.of(
                Arguments.of(
                        PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "createTime"))
                ),
                Arguments.of(
                        PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "createTime"))
                ),
                Arguments.of(
                        PageRequest.of(1, 2, Sort.by(Sort.Direction.ASC, "createTime"))
                )
        );
    }
}

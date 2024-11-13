package com.mehmetozanguven.criteria_api_with_spring_boot;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.User;
import com.mehmetozanguven.criteria_api_with_spring_boot.service.TestUserInitialization;
import com.mehmetozanguven.criteria_api_with_spring_boot.service.UserService;
import com.mehmetozanguven.criteria_api_with_spring_boot.testcontainer.EnableTestcontainers;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@EnableTestcontainers
@SpringBootTest
class SimpleQueryTest {
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
	}

	@Test
	void testSimpleQuery() {
		testUserInitialization.initializeTestUsers();

		List<User> allUsers = userService.getAllUsers();
		Assertions.assertEquals(TestUserInitialization.newUsers().count(), allUsers.size());

		List<String> allUsersEmails = userService.getAllUsersEmails();
		Assertions.assertEquals(TestUserInitialization.newUsers().count(), allUsersEmails.size());

		List<User> usersWithName = userService.findAllUsersWhereNameNotNull();
		Assertions.assertEquals(TestUserInitialization.newUsers().count(), usersWithName.size());

		List<User> userSearchByEmail = userService.getUsersWithEmailLike("test1");
		Assertions.assertEquals(1, userSearchByEmail.size());
	}

}

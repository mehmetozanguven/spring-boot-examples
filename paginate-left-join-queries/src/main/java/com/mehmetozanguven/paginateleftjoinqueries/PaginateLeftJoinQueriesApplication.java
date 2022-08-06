package com.mehmetozanguven.paginateleftjoinqueries;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.Student;
import com.mehmetozanguven.paginateleftjoinqueries.repository.SchoolRepository;
import com.mehmetozanguven.paginateleftjoinqueries.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RequiredArgsConstructor
public class PaginateLeftJoinQueriesApplication {
	private final SchoolRepository schoolRepository;
	private final StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(PaginateLeftJoinQueriesApplication.class, args);
	}

	@PostConstruct
	public void initDatabase() {
		int schoolCount = RandomUtils.nextInt(20, 100);
		for (int index = 0; index < schoolCount; index++) {
			School school = School
					.builder()
					.name(RandomStringUtils.randomAlphabetic(10))
					.build();
			school = schoolRepository.save(school);
			int studentCount = RandomUtils.nextInt(5, 50);
			for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) {
				Student student = Student
						.builder()
						.name(RandomStringUtils.randomAlphabetic(10))
						.school(school)
						.build();
				studentRepository.save(student);
			}
		}
	}
}

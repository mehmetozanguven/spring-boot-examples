package com.mehmetozanguven.paginateleftjoinqueries.service;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentCount;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentName;
import com.mehmetozanguven.paginateleftjoinqueries.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    @Transactional
    public void createSchool(School school) {
        schoolRepository.save(school);
    }

    public Page<School> findSchoolsInPage(Pageable pageable) {
        return schoolRepository.findAll(pageable);
    }

    public Page<SchoolWithStudentCount> findSchoolsWithStudentCountInPage(Pageable pageable) {
        return schoolRepository.findSchoolWithStudentCount(pageable);
    }

    public Page<SchoolWithStudentName> findSchoolWithStudentName(Pageable pageable) {
        return schoolRepository.findSchoolWithStudentName(pageable);
    }
}

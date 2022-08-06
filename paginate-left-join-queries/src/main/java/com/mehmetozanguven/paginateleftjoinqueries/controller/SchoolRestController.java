package com.mehmetozanguven.paginateleftjoinqueries.controller;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentCount;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentName;
import com.mehmetozanguven.paginateleftjoinqueries.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SchoolRestController {
    private final SchoolService schoolService;


    @GetMapping(value = "/find")
    public ResponseEntity<Page<School>> findSchoolsInPage(Pageable pageable) {
        Page<School> response = schoolService.findSchoolsInPage(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/find-with-student-count")
    public ResponseEntity<Page<SchoolWithStudentCount>> findSchoolsWithStudentCountInPage(Pageable pageable) {
        Page<SchoolWithStudentCount> response =schoolService.findSchoolsWithStudentCountInPage(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/find-with-student-name")
    public ResponseEntity<Page<SchoolWithStudentName>> findSchoolWithStudentName(Pageable pageable) {
        Page<SchoolWithStudentName> response = schoolService.findSchoolWithStudentName(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

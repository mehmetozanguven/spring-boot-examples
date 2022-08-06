package com.mehmetozanguven.paginateleftjoinqueries.service;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.Student;
import com.mehmetozanguven.paginateleftjoinqueries.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void createStudent(Student student) {
        studentRepository.save(student);
    }
}

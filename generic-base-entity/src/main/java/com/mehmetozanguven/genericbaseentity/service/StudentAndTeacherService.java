package com.mehmetozanguven.genericbaseentity.service;

import com.mehmetozanguven.genericbaseentity.entity.Student;
import com.mehmetozanguven.genericbaseentity.entity.Teacher;
import com.mehmetozanguven.genericbaseentity.repository.StudentRepository;
import com.mehmetozanguven.genericbaseentity.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StudentAndTeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }


    @Transactional
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
}

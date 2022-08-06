package com.mehmetozanguven.paginateleftjoinqueries.repository;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Long> {

}

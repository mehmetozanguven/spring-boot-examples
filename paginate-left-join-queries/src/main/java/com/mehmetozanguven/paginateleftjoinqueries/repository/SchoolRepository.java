package com.mehmetozanguven.paginateleftjoinqueries.repository;

import com.mehmetozanguven.paginateleftjoinqueries.entity.School;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentCount;
import com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query(
            value = "SELECT new com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentCount" +
                    "(" +
                    "sc, " +
                    "COUNT(st.id) AS studentCount" +
                    ") " +
                    "FROM School sc  " +
                    "LEFT JOIN Student st ON st.school = sc " +
                    "GROUP BY sc"
    )
    Page<SchoolWithStudentCount> findSchoolWithStudentCount(Pageable pageable);

    @Query(
            value = "SELECT new com.mehmetozanguven.paginateleftjoinqueries.entity.SchoolWithStudentName" +
                    "(" +
                    "sc, " +
                    "st.name AS studentName" +
                    ") " +
                    "FROM School sc  " +
                    "LEFT JOIN Student st ON st.school = sc "
    )
    Page<SchoolWithStudentName> findSchoolWithStudentName(Pageable pageable);
}

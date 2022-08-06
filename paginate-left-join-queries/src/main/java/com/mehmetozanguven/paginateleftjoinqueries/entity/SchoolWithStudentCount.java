package com.mehmetozanguven.paginateleftjoinqueries.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class SchoolWithStudentCount {
    private School school;
    private long studentCount;
}

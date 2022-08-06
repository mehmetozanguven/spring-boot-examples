package com.mehmetozanguven.paginateleftjoinqueries.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "school")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;
}

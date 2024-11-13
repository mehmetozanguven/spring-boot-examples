package com.mehmetozanguven.criteria_api_with_spring_boot.entity;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Table(name = "users_roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "version_id")
    @Version
    private Long versionId;
}

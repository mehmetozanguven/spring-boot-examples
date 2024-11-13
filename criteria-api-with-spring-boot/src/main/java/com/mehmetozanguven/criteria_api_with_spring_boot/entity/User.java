package com.mehmetozanguven.criteria_api_with_spring_boot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "version_id")
    @Version
    private Long versionId;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade ={
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users_roles_join", // table name
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> userRoles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Address> userAddresses = new HashSet<>();

}

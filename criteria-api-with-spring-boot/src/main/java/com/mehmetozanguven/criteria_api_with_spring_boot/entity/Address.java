package com.mehmetozanguven.criteria_api_with_spring_boot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Entity
@Table(name = "users_addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "version_id")
    @Version
    private Long versionId;
}

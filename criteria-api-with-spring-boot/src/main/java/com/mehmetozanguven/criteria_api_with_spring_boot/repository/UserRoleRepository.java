package com.mehmetozanguven.criteria_api_with_spring_boot.repository;

import com.mehmetozanguven.criteria_api_with_spring_boot.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}

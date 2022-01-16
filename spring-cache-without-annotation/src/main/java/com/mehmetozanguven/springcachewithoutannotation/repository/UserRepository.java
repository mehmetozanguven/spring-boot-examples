package com.mehmetozanguven.springcachewithoutannotation.repository;

import com.mehmetozanguven.springcachewithoutannotation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE u.firstName = ?1")
    Optional<User> findByFirstName(String firstName);
}

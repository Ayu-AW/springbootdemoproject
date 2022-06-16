package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
        Third step: create a repository with a custom query method to find all user auth groups
 */
@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
    List<AuthGroup> findByaEmail(String email);

}

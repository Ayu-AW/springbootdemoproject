package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {


    // select * from auth_group where aemail = "";
    List<AuthGroup> findByaEmail(String email);
}

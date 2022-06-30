package com.instructor.springbootdemoproject.security;


import com.instructor.springbootdemoproject.data.AuthGroupRepository;
import com.instructor.springbootdemoproject.models.AuthGroup;
import com.instructor.springbootdemoproject.models.Student;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    AuthGroupRepository authGroupRepository;
    StudentService studentService;

    @Autowired
    public AppUserDetailsService(AuthGroupRepository authGroupRepository, StudentService studentService) {
        this.authGroupRepository = authGroupRepository;
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<AuthGroup> authGroupList = authGroupRepository.findByaEmail(username);
        Student s = studentService.findByEmail(username);

        return new AppUserPrincipal(s, authGroupList);
    }
}

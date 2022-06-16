package com.instructor.springbootdemoproject.security;

import com.instructor.springbootdemoproject.data.AuthGroupRepository;
import com.instructor.springbootdemoproject.models.AuthGroup;
import com.instructor.springbootdemoproject.models.Student;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
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
        Student student = null;
        try {
             student = studentService.findByEmail(username);

        } catch (NoSuchElementException ex){
            log.warn("Couldn't find email: " + username);
            ex.printStackTrace();
        } catch(UsernameNotFoundException e){
            log.warn("Couldn't find email: " + username);
            e.printStackTrace();
        }
        List<AuthGroup> authGroups = authGroupRepository.findByaEmail(username);
        return new AppUserPrincipal(student, authGroups);
    }
}

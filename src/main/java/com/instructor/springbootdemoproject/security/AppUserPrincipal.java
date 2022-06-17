package com.instructor.springbootdemoproject.security;

import com.instructor.springbootdemoproject.models.AuthGroup;
import com.instructor.springbootdemoproject.models.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppUserPrincipal implements UserDetails {

    private Student student;
    private List<AuthGroup> authGroup;

    public AppUserPrincipal(Student student, List<AuthGroup> authGroup) {
        this.student = student;
        this.authGroup = authGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        Set<SimpleGrantedAuthority> authGroupSet = new HashSet<>();
        //authGroup.forEach(auth -> authGroupSet.add(new SimpleGrantedAuthority(auth.getRole())));
        for (AuthGroup auth: authGroup) {
            authGroupSet.add(new SimpleGrantedAuthority(auth.getRole()));
        }
        return authGroupSet;
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

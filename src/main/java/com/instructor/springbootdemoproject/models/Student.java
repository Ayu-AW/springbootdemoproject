package com.instructor.springbootdemoproject.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jafer alhaoubi
 */

@NoArgsConstructor
@AllArgsConstructor

@Getter @Setter @Slf4j @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student")
@Entity
public class Student {

    // Fields

    @Id @Email(message = "Must be a valid email")
    String email;

    @NotBlank(message = "Provide full name")
    String name;

    @Setter(AccessLevel.NONE)
    String password;

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> courses = new LinkedHashSet<>();


    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    // Helper Method
    public void addCourse(Course course){
        courses.add(course);
        course.getStudents().add(this);
    }

    // Hashcode and Equals override


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email.equals(student.email) && name.equals(student.name) && password.equals(student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password);
    }


}

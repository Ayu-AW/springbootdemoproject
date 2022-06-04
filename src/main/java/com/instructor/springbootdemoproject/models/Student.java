package com.instructor.springbootdemoproject.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jafer alhaoubi
 */

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student")
@Entity
public class Student {

    // Fields

    @Id @NonNull
    String email;
    @NonNull
    String name;
    @NonNull
    String password;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> courses = new LinkedHashSet<>();

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

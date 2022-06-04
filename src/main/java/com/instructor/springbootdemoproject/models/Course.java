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
@Table(name = "course")
@Entity
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String name;
    @NonNull
    String instructor;

    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    Set<Student> students = new LinkedHashSet<>();

    // Helper Method
    public void addStudent(Student student){
        students.add(student);
        student.getCourses().add(this);
    }

    // Hashcode and Equals override


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && name.equals(course.name) && instructor.equals(course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor);
    }
}

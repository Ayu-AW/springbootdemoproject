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
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "course")

/*
    Resource:
    https://thorben-janssen.com/spring-data-jpa-dto-native-queries/
    @NamedNativeQuery & @SqlResultSetMapping
*/

@NamedNativeQuery(name = "Course.findStudentCourses", query = "select c.id, c.name, c.instructor from course as c join student_courses as sc on c.id = sc.course_id join student as s on s.email = sc.student_email where s.email = :email", resultSetMapping = "Mapping.findStudentCourses")
@SqlResultSetMapping(name = "Mapping.findStudentCourses", classes = @ConstructorResult(targetClass = Course.class, columns = {@ColumnResult(name = "id"), @ColumnResult(name = "name"), @ColumnResult(name = "instructor")}))

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull String name;
    @NonNull String instructor;
    @ToString.Exclude
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    Set<Student> students = new LinkedHashSet<>();

    //constructor


    public Course(int id, @NonNull String name, @NonNull String instructor) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
    }

    // Helper Method
    public void addStudent(Student student) {
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

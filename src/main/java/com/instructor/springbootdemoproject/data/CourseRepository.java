package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(nativeQuery = true)
    List<Course> findStudentCourses(String email);

    @Query(value = "select * from course where id  > :id",nativeQuery = true)
    List<Course> findAllCourseGreaterThanId(int id);

    Optional<Course> findByName(String name);



}

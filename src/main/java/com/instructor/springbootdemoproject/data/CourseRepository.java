package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(nativeQuery = true)
    public List<Course> findStudentCourses(String email);


}

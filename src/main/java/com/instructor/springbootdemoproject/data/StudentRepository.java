package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.Course;
import com.instructor.springbootdemoproject.models.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}

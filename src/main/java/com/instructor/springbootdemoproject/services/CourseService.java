package com.instructor.springbootdemoproject.services;

import com.instructor.springbootdemoproject.data.CourseRepository;
import com.instructor.springbootdemoproject.data.StudentRepository;
import com.instructor.springbootdemoproject.models.Course;
import com.instructor.springbootdemoproject.models.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class CourseService {


    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Autowired
    public CourseService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }
    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Course findById(int id) throws NoSuchElementException{
        return courseRepository.findById(id).orElseThrow();
    }

    public void saveOrUpdate(Course c){
        courseRepository.save(c);
    }

    public void delete(Course c){
        courseRepository.delete(c);
    }

    public List<Course> getStudentCourses(String email){
        return courseRepository.findStudentCourses(email);
    }
}

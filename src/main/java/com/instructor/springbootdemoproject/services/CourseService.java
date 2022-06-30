package com.instructor.springbootdemoproject.services;

import com.instructor.springbootdemoproject.data.CourseRepository;
import com.instructor.springbootdemoproject.data.StudentRepository;
import com.instructor.springbootdemoproject.models.Course;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class CourseService {


    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Autowired
    public CourseService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Course findById(int id) throws NoSuchElementException {
        return courseRepository.findById(id).orElseThrow();
    }

    public void saveOrUpdate(Course c) {
        courseRepository.save(c);
        log.info(String.format("Course ID Generated: %d Course Name: %s", c.getId(), c.getName()));
    }

    public void delete(Course c) {
        courseRepository.delete(c);
    }

    public List<Course> getStudentCourses(String email) {
        return courseRepository.findStudentCourses(email);
    }

    public Course findCourseByName(String name) {
        return courseRepository.findByName(name).orElseThrow();
    }
}

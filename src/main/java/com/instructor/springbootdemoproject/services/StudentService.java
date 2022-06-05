package com.instructor.springbootdemoproject.services;

import com.instructor.springbootdemoproject.data.CourseRepository;
import com.instructor.springbootdemoproject.data.StudentRepository;
import com.instructor.springbootdemoproject.models.Course;
import com.instructor.springbootdemoproject.models.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class StudentService {

    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Student findByEmail(String email) throws NoSuchElementException{
        return studentRepository.findById(email).orElseThrow();
    }

    public void saveOrUpdate(Student s){
        studentRepository.save(s);
    }

    public void delete(Student s){
        studentRepository.delete(s);
    }
    @Transactional(rollbackOn = {NoSuchElementException.class})
    public void addCourse(String email, Course c) throws NoSuchElementException{

        Student s = studentRepository.findById(email).orElseThrow();
        s.addCourse(c);
        studentRepository.save(s);
    }
/*
    Resource:
    https://www.baeldung.com/spring-data-sorting
    To sort by field
*/
    public List<Student> findAllSortedBy(Sort sort){
        return studentRepository.findAll(sort);
    }


}

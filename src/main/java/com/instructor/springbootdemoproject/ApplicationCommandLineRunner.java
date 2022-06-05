package com.instructor.springbootdemoproject;

import com.instructor.springbootdemoproject.models.Course;
import com.instructor.springbootdemoproject.models.Student;
import com.instructor.springbootdemoproject.services.CourseService;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component @Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ApplicationCommandLineRunner implements CommandLineRunner {

    StudentService studentService;
    CourseService courseService;
    String PASSWORD = "password";
    String JAFER_ID = "jafer@gmail.com";
    String KEVIN_ID ="kevin@gmail.com";
    @Autowired
    public ApplicationCommandLineRunner(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostConstruct
    public void postConstruct(){
        log.warn("============ Application CommandLine Runner ============");
    }

    @Override
    public void run(String... args) throws Exception {
        studentService.saveOrUpdate(new Student(JAFER_ID, "Jafer", PASSWORD));
        studentService.saveOrUpdate(new Student("kevin@gmail.com", "Kevin", PASSWORD));
        studentService.saveOrUpdate(new Student("nick@gmail.com", "Nick", PASSWORD));

        courseService.saveOrUpdate(new Course("Java","Jafer"));
        courseService.saveOrUpdate(new Course("Springboot","Kasper"));
        courseService.saveOrUpdate(new Course("SQL","Phillip"));

        try {
            studentService.addCourse(JAFER_ID, courseService.findById(3));
            studentService.addCourse(JAFER_ID, courseService.findById(2));
            studentService.addCourse(KEVIN_ID, courseService.findById(2));
        } catch (NoSuchElementException ex){
            log.error("Couldn't add course to student!");
            ex.printStackTrace();
        }
        log.warn("Results...");
        log.warn(studentService.findByEmail(JAFER_ID).getCourses().toString());
        log.warn(studentService.findByEmail(KEVIN_ID).getCourses().toString());
        log.warn(courseService.getStudentCourses(JAFER_ID).toString());
        log.warn(studentService.findAllSortedBy(Sort.by(Sort.Direction.DESC, "name")).toString());
    }
}

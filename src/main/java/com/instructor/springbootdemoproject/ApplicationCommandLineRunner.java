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
        studentService.saveOrUpdate(new Student("reema@gmail.com", "Reema", PASSWORD));
        studentService.saveOrUpdate(new Student("roger@gmail.com", "roger", PASSWORD));

        courseService.saveOrUpdate(new Course("Java","Jafer"));
        courseService.saveOrUpdate(new Course("Springboot","Kasper"));
        courseService.saveOrUpdate(new Course("SQL","Phillip"));
        courseService.saveOrUpdate(new Course("AWS","Leon"));
        courseService.saveOrUpdate(new Course("JUnit","Charlie"));

        try {
            studentService.addCourse(JAFER_ID, courseService.findById(1));
            studentService.addCourse(JAFER_ID, courseService.findById(2));
            studentService.addCourse(JAFER_ID, courseService.findById(3));
            studentService.addCourse(KEVIN_ID, courseService.findById(1));
            studentService.addCourse(KEVIN_ID, courseService.findById(2));
            studentService.addCourse(KEVIN_ID, courseService.findById(3));
            studentService.addCourse(KEVIN_ID, courseService.findById(4));
            studentService.addCourse(KEVIN_ID, courseService.findById(5));
            studentService.addCourse("roger@gmail.com", courseService.findById(1));
            studentService.addCourse("roger@gmail.com", courseService.findById(2));
        } catch (NoSuchElementException ex){
            log.error("Couldn't add course to student!");
            ex.printStackTrace();
        } catch (RuntimeException e){
            log.error("Couldn't add courses!");
            e.printStackTrace();
        }
        log.warn("Results...");
        log.warn(studentService.findByEmail(JAFER_ID).getCourses().toString());
        log.warn(studentService.findByEmail(KEVIN_ID).getCourses().toString());
        log.warn(courseService.getStudentCourses(JAFER_ID).toString());
        log.warn(studentService.findAllSortedBy(Sort.by(Sort.Direction.DESC, "name")).toString());
    }
}

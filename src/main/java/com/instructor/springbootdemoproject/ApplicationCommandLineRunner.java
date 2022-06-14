package com.instructor.springbootdemoproject;

import com.instructor.springbootdemoproject.data.CourseRepository;
import com.instructor.springbootdemoproject.data.StudentRepository;
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
import java.util.NoSuchElementException;

@Component @Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ApplicationCommandLineRunner implements CommandLineRunner {

    StudentService studentService;
    CourseService courseService;

    CourseRepository courseRepository;
    static final  String PASSWORD = "password";
    static final  String JAFERID = "jafer@gmail.com";
    static final  String KEVINID ="kevin@gmail.com";
    static final  String ROGERID = "roger@gmail.com";
    @Autowired
    public ApplicationCommandLineRunner(StudentService studentService, CourseService courseService, CourseRepository courseRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }







    @PostConstruct
    public void postConstruct(){
        log.warn("============ Application CommandLine Runner ============");
    }

    @Override
    public void run(String... args) throws Exception {


        studentService.saveOrUpdate(new Student(JAFERID, "Jafer", PASSWORD));
        studentService.saveOrUpdate(new Student(KEVINID, "Kevin", PASSWORD));
        studentService.saveOrUpdate(new Student("nick@gmail.com", "Nick", PASSWORD));
        studentService.saveOrUpdate(new Student("reema@gmail.com", "Reema", PASSWORD));
        studentService.saveOrUpdate(new Student(ROGERID, "roger", PASSWORD));

        courseService.saveOrUpdate(new Course("Java","Jafer"));
        courseService.saveOrUpdate(new Course("Springboot","Kasper"));
        courseService.saveOrUpdate(new Course("SQL","Phillip"));
        courseService.saveOrUpdate(new Course("AWS","Leon"));
        courseService.saveOrUpdate(new Course("JUnit","Charlie"));

        try {
            studentService.addCourse(JAFERID, courseService.findById(1));
            studentService.addCourse(JAFERID, courseService.findById(2));
            studentService.addCourse(JAFERID, courseService.findById(3));
            studentService.addCourse(KEVINID, courseService.findById(1));
            studentService.addCourse(KEVINID, courseService.findById(2));
            studentService.addCourse(KEVINID, courseService.findById(3));
            studentService.addCourse(KEVINID, courseService.findById(4));
            studentService.addCourse(KEVINID, courseService.findById(5));
            studentService.addCourse(ROGERID, courseService.findById(1));
            studentService.addCourse(ROGERID, courseService.findById(2));
        } catch (NoSuchElementException ex){
            log.error("Couldn't add course to student!");
            ex.printStackTrace();
        } catch (RuntimeException e){
            log.error("Couldn't add courses!");
            e.printStackTrace();
        }
        log.info("Find All Students Sorted By Name Desc");
        log.warn(studentService.findAllSortedBy(Sort.by(Sort.Direction.DESC, "name")).toString());

        log.warn("findAllCourseGreaterThanId: " + courseRepository.findAllCourseGreaterThanId(3).toString());

    }
}

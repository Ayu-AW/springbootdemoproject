package com.instructor.springbootdemoproject.controllers;

import com.instructor.springbootdemoproject.models.Course;
import com.instructor.springbootdemoproject.models.Student;
import com.instructor.springbootdemoproject.services.CourseService;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;

@Controller @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "students")
public class StudentController {
    StudentService studentService;
    CourseService courseService;
    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }



    @GetMapping
    public String getAllStudents(Model model){
        model.addAttribute("students",studentService.findAll());
        return "students";
    }

    @PostMapping("/findstudentusername")
    public RedirectView findStudentUsername(@RequestParam(required = false) String email, RedirectAttributes redirectAttributes){
        log.warn("email: " + email);
        try {
            redirectAttributes.addFlashAttribute("user", studentService.findByEmail(email));
        } catch (RuntimeException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("user_not_found",String.format("Username: %s not found!",email));
            return new RedirectView("/students");
        }
        return new RedirectView("/students");
    }



    @GetMapping(value="/studentform")
    public String studentForm(Model model){
        model.addAttribute("student",new Student());
        return "studentcreateupdate";
    }

    @PostMapping("/saveupdatestudent")
    public String saveUpdateStudent(RedirectAttributes model, @ModelAttribute("student") Student student){
        log.warn("Model student: "+ student);
        studentService.saveOrUpdate(student);
        model.addFlashAttribute("student",studentService.findByEmail(student.getEmail()));
        return "studentcreateupdate";
    }

    @GetMapping("/registertocourse/{email}")
    public String registerStudentToCourse(@PathVariable String email, Model model){
        model.addAttribute("student",studentService.findByEmail(email));
        // courses available to register
        Set<Course> studentNotRegisteredToThisCourses = new HashSet<>(courseService.findAll());
        studentNotRegisteredToThisCourses.removeAll(courseService.getStudentCourses(email));
        log.info(studentNotRegisteredToThisCourses.toString());
        model.addAttribute("studentNotRegisteredToThisCourses",studentNotRegisteredToThisCourses);
        return "registertocourse";
    }


}

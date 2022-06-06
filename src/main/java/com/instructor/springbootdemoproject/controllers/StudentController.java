package com.instructor.springbootdemoproject.controllers;

import com.instructor.springbootdemoproject.services.CourseService;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    StudentService studentService;
    CourseService courseService;
    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(){
        return "index";
    }

    @GetMapping(value = {"students"})
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
            return new RedirectView("students");
        }
        return new RedirectView("students");
    }
}

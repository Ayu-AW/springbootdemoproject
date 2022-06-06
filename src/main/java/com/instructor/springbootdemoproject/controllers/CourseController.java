package com.instructor.springbootdemoproject.controllers;

import com.instructor.springbootdemoproject.services.CourseService;
import com.instructor.springbootdemoproject.services.StudentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CourseController {

    StudentService studentService;
    CourseService courseService;

    @Autowired
    public CourseController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping(value = {"courses"})
    public String getAllStudents(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }
}

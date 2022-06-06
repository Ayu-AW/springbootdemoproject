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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller @Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("courses")
public class CourseController {

    StudentService studentService;
    CourseService courseService;

    @Autowired
    public CourseController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }

    @GetMapping(value="/courseform")
    public String courseForm(Model model){
        model.addAttribute("course",new Course());
        return "coursecreateupdate";
    }

    @PostMapping("/saveupdatecourse")
    public String saveUpdateCourse(RedirectAttributes model, @ModelAttribute("course") Course course){
        log.warn("Model course: "+ course);
        courseService.saveOrUpdate(course);
        model.addFlashAttribute("course",courseService.findById(course.getId()));
        return "coursecreateupdate";
    }
}

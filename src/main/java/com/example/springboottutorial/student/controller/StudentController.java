package com.example.springboottutorial.student.controller;

import com.example.springboottutorial.student.controller.dto.Student;
import com.example.springboottutorial.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    @GetMapping(path = "{studentID}", produces = MediaType.APPLICATION_JSON_VALUE )
    public Student getStudent(@PathVariable("studentID") Long id) {
        return this.studentService.getStudent(id);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody com.example.springboottutorial.student.entity.Student student) {
        this.studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable("studentID") Long id) {
        this.studentService.deleteStudent(id);
    }

    //TODO: StudentUpdateDTO mit Felder die im Update erlaubt sind
    @PutMapping(path =  "{studentID}")
    public void updateStudent(@PathVariable("studentID") Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        this.studentService.updateStudent(id, name, email);
    }
}

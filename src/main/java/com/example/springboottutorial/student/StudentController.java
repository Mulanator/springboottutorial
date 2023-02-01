package com.example.springboottutorial.student;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<StudentDTO> getStudents() {
        return this.studentService.getStudents();
    }

    @GetMapping(path = "{studentID}")
    public StudentDTO getStudent(@PathVariable("studentID") Long id) {
        return this.studentService.getStudent(id);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        this.studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable("studentID") Long id) {
        this.studentService.deleteStudent(id);
    }

    @PutMapping(path =  "{studentID}")
    public void updateStudent(@PathVariable("studentID") Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        this.studentService.updateStudent(id, name, email);
    }
}

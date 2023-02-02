package com.example.springboottutorial.student.entity;

import com.example.springboottutorial.student.controller.dto.Student;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<com.example.springboottutorial.student.entity.Student, Student> {

    @Override
    public Student apply(com.example.springboottutorial.student.entity.Student student) {
        return new Student(
                student.getId(),
                student.getName(),
                student.getEmail()
        );
    }
}

package com.example.springboottutorial.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentDTOMapper studentDTOMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentDTOMapper studentDTOMapper) {

        this.studentRepository = studentRepository;
        this.studentDTOMapper = studentDTOMapper;
    }

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll().
                stream().map(studentDTOMapper).collect(Collectors.toList());
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already taken.");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Student " + id + " does not exist.");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student " + id + " does not exist."));

        if (name != null) {
            student.setName(name);
        }

        if (email != null) {
            student.setEmail(email);
        }
    }

    public StudentDTO getStudent(Long Id) {
        //@TODO: exception not working
        Optional<Student> studentOptional = Optional.ofNullable(Optional.of(studentRepository.getReferenceById(Id))
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)));
        return studentOptional.map(studentDTOMapper).orElse(null);
    }
}

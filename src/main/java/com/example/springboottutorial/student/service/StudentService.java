package com.example.springboottutorial.student.service;

import com.example.springboottutorial.student.repository.StudentRepository;
import com.example.springboottutorial.student.controller.dto.Student;
import com.example.springboottutorial.student.entity.StudentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentDTOMapper studentDTOMapper;

    /* wird durch requiredargsconstructor annotation von lombok generiert
    @Autowired
    public StudentService(StudentRepository studentRepository, StudentDTOMapper studentDTOMapper) {

        this.studentRepository = studentRepository;
        this.studentDTOMapper = studentDTOMapper;
    }
*/

    public List<Student> getStudents() {
        return studentRepository.findAll().
                stream().map(studentDTOMapper).collect(Collectors.toList());
    }

    public void addNewStudent(com.example.springboottutorial.student.entity.Student student) {
        Optional<com.example.springboottutorial.student.entity.Student> studentOptional = studentRepository.findByEmail(student.getEmail());
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
        com.example.springboottutorial.student.entity.Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student " + id + " does not exist."));

        if (name != null) {
            student.setName(name);
        }

        if (email != null) {
            student.setEmail(email);
        }
        studentRepository.save(student);
    }

    public Student getStudent(Long Id) {
        return studentDTOMapper.apply(studentRepository.findById(Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}

package com.example.springboottutorial.student.repository;

import com.example.springboottutorial.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource; //spring-boot-data-rest package

import java.util.Optional;

@RepositoryRestResource(exported=false)
public interface StudentRepository extends JpaRepository<Student, Long> {

    //@Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findByEmail(String eMail);
}

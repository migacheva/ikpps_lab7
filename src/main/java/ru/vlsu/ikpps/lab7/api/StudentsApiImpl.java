package ru.vlsu.ikpps.lab7.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vlsu.ikpps.lab7.model.Student;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentsApiImpl implements StudentsApi {

    List<Student> students = new ArrayList<>();

    @Override
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student body) {
        students.add(body);
        return new ResponseEntity<Student>(body, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Integer id) {
        Optional<Student> student = students.stream().filter(s -> id.equals(s.getId())).findFirst();
        return student.map(s -> {
            students.remove(student.get());
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Student> readStudent(@PathVariable("id") Integer id) {
        Optional<Student> student = students.stream().filter(s -> id.equals(s.getId())).findFirst();
        return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<Student>> readStudents() {
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student body) {
        Optional<Student> student = students.stream().filter(s -> body.getId().equals(s.getId())).findFirst();
        return student.map(s -> {
            s.setName(body.getName());
            s.setGroup(body.getGroup());
            return new ResponseEntity<Student>(s, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
    }
}

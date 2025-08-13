package com.pos.laborator.services;

import com.pos.laborator.data.Disciplina;
import com.pos.laborator.data.Student;
import com.pos.laborator.data.stare;
import com.pos.laborator.dto.StudentDto;
import com.pos.laborator.repository.DisciplinaRepository;
import com.pos.laborator.repository.StudentRepository;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Student addStudent(Student student) {
        if (student.get_id() == null) {
            student.set_id(new ObjectId());
        }
        Student savedStudent = studentRepository.save(student);

        student.getNote().forEach((disciplinaName, nota) -> {
            Optional<Disciplina> disciplinaOpt = disciplinaRepository.findByDenumireProgramStudii(disciplinaName);

            if (disciplinaOpt.isPresent()) {
                Disciplina disciplina = disciplinaOpt.get();

                if (disciplina.getStudenti().stream().noneMatch(s -> s.get_id() != null && s.get_id().equals(savedStudent.get_id()))) {
                    disciplina.getStudenti().add(savedStudent);
                    disciplinaRepository.save(disciplina);
                }
            } else {
                throw new RuntimeException("Disciplina " + disciplinaName + " nu exista.");
            }
        });

        return savedStudent;
    }


    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(new ObjectId(id));
    }

    public Page<Student> getAllStudentsPaged(int page, int studentsPerPage) {
        if (page <= 0 || studentsPerPage <= 0) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page - 1, studentsPerPage);
        return studentRepository.findAll(pageable);
    }
    public List<Student> getStudentsByName(String nume) {
        return studentRepository.findByNumeIgnoreCase(nume);
    }

    public Optional<Student> updateStudent(String id, Student updatedStudent) {
        return studentRepository.findById(new ObjectId(id))
                .map(student -> {
                    student.setPrenume(updatedStudent.getPrenume());
                    student.setNume(updatedStudent.getNume());
                    student.setStare(updatedStudent.getStare());
                    student.setMedie(updatedStudent.getMedie());

                    student.setNote(updatedStudent.getNote());

                    updatedStudent.getNote().forEach((disciplinaName, nota) -> {
                        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findByDenumireProgramStudii(disciplinaName);

                        if (disciplinaOpt.isPresent()) {
                            Disciplina disciplina = disciplinaOpt.get();

                            if (disciplina.getStudenti().stream().noneMatch(s -> s.get_id() != null && s.get_id().equals(student.get_id()))) {
                                disciplina.getStudenti().add(student);
                                disciplinaRepository.save(disciplina);
                            }
                        } else {
                            throw new RuntimeException("Disciplina " + disciplinaName + " nu exista.");
                        }
                    });

                    return studentRepository.save(student);
                });
    }
    public void deleteStudent(String id) {
        Optional<Student> studentOpt = studentRepository.findById(new ObjectId(id));

        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Studentul cu id-ul " + id + " nu a fost gasit.");
        }

        Student student = studentOpt.get();

        List<Disciplina> discipline = disciplinaRepository.findAll();
        for (Disciplina disciplina : discipline) {
            if (disciplina.getStudenti() == null) {
                disciplina.setStudenti(new ArrayList<>());
            }

            boolean removed = disciplina.getStudenti().removeIf(s -> s.get_id() != null && s.get_id().equals(student.get_id()));

            if (removed) {
                disciplinaRepository.save(disciplina);
            }
        }

        studentRepository.delete(student);
    }

}


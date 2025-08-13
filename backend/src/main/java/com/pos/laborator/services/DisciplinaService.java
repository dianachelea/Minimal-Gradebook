package com.pos.laborator.services;


import com.pos.laborator.data.Disciplina;
import com.pos.laborator.data.Student;
import com.pos.laborator.repository.DisciplinaRepository;
import com.pos.laborator.repository.StudentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private StudentRepository studentRepository;
    public Disciplina addDisciplina(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Optional<Disciplina> getDisciplinaById(String id) {
        return disciplinaRepository.findById(new ObjectId(id));
    }

    public List<Disciplina> getAllDiscipline() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> updateDisciplina(String id, Disciplina updatedDisciplina) {
        return disciplinaRepository.findById(new ObjectId(id))
                .map(disciplina -> {
                    String oldName = disciplina.getDenumire_program_studii();

                    disciplina.setDenumire_program_studii(updatedDisciplina.getDenumire_program_studii());
                    disciplina.setAn_de_studiu(updatedDisciplina.getAn_de_studiu());
                    disciplina.setNume_titular(updatedDisciplina.getNume_titular());
                    disciplina.setGrad_didactic(updatedDisciplina.getGrad_didactic());

                    disciplinaRepository.save(disciplina);

                    updateStudentNotes(oldName, updatedDisciplina.getDenumire_program_studii());

                    return disciplina;
                });
    }

    private void updateStudentNotes(String oldName, String newName) {
        List<Student> studenti = studentRepository.findAll();

        for (Student student : studenti) {
            if (student.getNote() != null && student.getNote().containsKey(oldName)) {
                Integer grade = student.getNote().remove(oldName);
                student.getNote().put(newName, grade);
                studentRepository.save(student);
            }
        }
    }

    public boolean deleteDisciplina(String id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(new ObjectId(id));

        if (disciplinaOpt.isEmpty()) {
            return false;
        }

        Disciplina disciplina = disciplinaOpt.get();

        List<Student> studenti = studentRepository.findAll();

        for (Student student : studenti) {
            if (student.getNote() != null && student.getNote().containsKey(disciplina.getDenumire_program_studii())) {
                student.getNote().remove(disciplina.getDenumire_program_studii());
                studentRepository.save(student);
            }
        }

        disciplinaRepository.deleteById(new ObjectId(id));
        return true;
    }




}

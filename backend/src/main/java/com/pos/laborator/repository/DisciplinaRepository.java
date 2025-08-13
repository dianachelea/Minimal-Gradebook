package com.pos.laborator.repository;

import com.pos.laborator.data.Disciplina;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


import com.pos.laborator.data.Disciplina;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface DisciplinaRepository extends MongoRepository<Disciplina, ObjectId> {
    Optional<Disciplina> findByDenumireProgramStudii(String denumireProgramStudii);
}



package com.pos.laborator.repository;

import com.pos.laborator.data.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, ObjectId> {
    List<Student> findByNumeIgnoreCase(String nume);
}

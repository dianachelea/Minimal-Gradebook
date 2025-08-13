package com.pos.laborator.mapper;

import com.pos.laborator.data.Student;
import com.pos.laborator.dto.StudentDto;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IStudentMapper {

    @Mapping(target = "_id", expression = "java(studentDto.get_id() != null ? studentDto.get_id() : new org.bson.types.ObjectId())")
    Student toStudent(StudentDto studentDto);

    StudentDto fromStudent(Student student);
}


package com.pos.laborator.mapper;

import com.pos.laborator.data.Student;
import com.pos.laborator.data.stare;
import com.pos.laborator.dto.StudentDto;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-12T15:34:43+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class IStudentMapperImpl implements IStudentMapper {

    @Override
    public Student toStudent(StudentDto studentDto) {
        if ( studentDto == null ) {
            return null;
        }

        Student student = new Student();

        student.setPrenume( studentDto.getPrenume() );
        student.setNume( studentDto.getNume() );
        if ( studentDto.getStare() != null ) {
            student.setStare( Enum.valueOf( stare.class, studentDto.getStare() ) );
        }
        student.setId_catalog_promovat( studentDto.getId_catalog_promovat() );
        Map<String, Integer> map = studentDto.getNote();
        if ( map != null ) {
            student.setNote( new HashMap<String, Integer>( map ) );
        }
        student.setMedie( studentDto.getMedie() );

        student.set_id( studentDto.get_id() != null ? studentDto.get_id() : new org.bson.types.ObjectId() );

        return student;
    }

    @Override
    public StudentDto fromStudent(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDto studentDto = new StudentDto();

        studentDto.setPrenume( student.getPrenume() );
        studentDto.setNume( student.getNume() );
        if ( student.getStare() != null ) {
            studentDto.setStare( student.getStare().name() );
        }
        studentDto.setId_catalog_promovat( student.getId_catalog_promovat() );
        Map<String, Integer> map = student.getNote();
        if ( map != null ) {
            studentDto.setNote( new HashMap<String, Integer>( map ) );
        }
        studentDto.setMedie( student.getMedie() );
        studentDto.set_id( student.get_id() );

        return studentDto;
    }
}

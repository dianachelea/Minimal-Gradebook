package com.pos.laborator.mapper;

import com.pos.laborator.data.Disciplina;
import com.pos.laborator.data.Student;
import com.pos.laborator.dto.DisciplinaDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-12T15:34:43+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class IDisciplinaMapperImpl implements IDisciplinaMapper {

    @Override
    public Disciplina toDisciplina(DisciplinaDto disciplinaDto) {
        if ( disciplinaDto == null ) {
            return null;
        }

        Disciplina disciplina = new Disciplina();

        disciplina.set_id( disciplinaDto.get_id() );
        disciplina.setDenumire_program_studii( disciplinaDto.getDenumire_program_studii() );
        disciplina.setAn_de_studiu( disciplinaDto.getAn_de_studiu() );
        disciplina.setNume_titular( disciplinaDto.getNume_titular() );
        disciplina.setGrad_didactic( disciplinaDto.getGrad_didactic() );
        List<Student> list = disciplinaDto.getStudenti();
        if ( list != null ) {
            disciplina.setStudenti( new ArrayList<Student>( list ) );
        }

        return disciplina;
    }

    @Override
    public DisciplinaDto fromDisciplina(Disciplina disciplina) {
        if ( disciplina == null ) {
            return null;
        }

        DisciplinaDto disciplinaDto = new DisciplinaDto();

        disciplinaDto.set_id( disciplina.get_id() );
        disciplinaDto.setDenumire_program_studii( disciplina.getDenumire_program_studii() );
        disciplinaDto.setAn_de_studiu( disciplina.getAn_de_studiu() );
        disciplinaDto.setNume_titular( disciplina.getNume_titular() );
        disciplinaDto.setGrad_didactic( disciplina.getGrad_didactic() );
        List<Student> list = disciplina.getStudenti();
        if ( list != null ) {
            disciplinaDto.setStudenti( new ArrayList<Student>( list ) );
        }

        return disciplinaDto;
    }
}

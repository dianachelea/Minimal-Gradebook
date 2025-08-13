package com.pos.laborator.mapper;

import com.pos.laborator.data.Disciplina;
import com.pos.laborator.dto.DisciplinaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface IDisciplinaMapper {
    Disciplina toDisciplina(DisciplinaDto disciplinaDto);
    DisciplinaDto fromDisciplina(Disciplina disciplina);
}

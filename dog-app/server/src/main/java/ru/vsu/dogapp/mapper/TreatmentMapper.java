package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import ru.vsu.dogapp.dto.TreatmentDto;
import ru.vsu.dogapp.entity.Treatment;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    TreatmentDto toDto(Treatment treatment);
    Treatment toEntity(TreatmentDto treatmentDto);
}

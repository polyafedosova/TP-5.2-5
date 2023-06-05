package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.vsu.dogapp.dto.TreatmentDto;
import ru.vsu.dogapp.entity.Treatment;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TreatmentMapper {

    TreatmentDto toDto(Treatment treatment);
    Treatment toEntity(TreatmentDto treatmentDto);
}

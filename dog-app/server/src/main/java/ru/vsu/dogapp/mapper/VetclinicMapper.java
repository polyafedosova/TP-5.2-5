package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Vetclinic;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VetclinicMapper {

    VetclinicDto toDto(Vetclinic vetclinic);
    Vetclinic toEntity(VetclinicDto vetclinicDto);
}

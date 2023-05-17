package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import ru.vsu.dogapp.dto.VetclinicDto;
import ru.vsu.dogapp.entity.Vetclinic;

@Mapper(componentModel = "spring")
public interface VetclinicMapper {

    VetclinicDto toDto(Vetclinic vetclinic);
    Vetclinic toEntity(VetclinicDto vetclinicDto);
}

package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerDto toDto(Owner owner);
    Owner toEntity(OwnerDto ownerDto);
}

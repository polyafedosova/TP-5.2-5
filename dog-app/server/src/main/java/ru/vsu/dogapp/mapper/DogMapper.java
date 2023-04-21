package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.entity.Dog;

@Mapper(componentModel = "spring")
public interface DogMapper {

    DogDto toDto(Dog dog);
    Dog toEntity(DogDto dogDto);
}

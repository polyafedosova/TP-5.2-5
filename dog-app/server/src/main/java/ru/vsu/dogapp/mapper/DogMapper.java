package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.vsu.dogapp.dto.DogDto;
import ru.vsu.dogapp.entity.Dog;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DogMapper {

    DogDto toDto(Dog dog);
    Dog toEntity(DogDto dogDto);
}

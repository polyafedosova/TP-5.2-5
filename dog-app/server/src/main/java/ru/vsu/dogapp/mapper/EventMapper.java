package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.entity.Event;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface EventMapper {

    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}

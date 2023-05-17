package ru.vsu.dogapp.mapper;

import org.mapstruct.Mapper;
import ru.vsu.dogapp.dto.EventDto;
import ru.vsu.dogapp.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto toDto(Event event);
    Event toEntity(EventDto eventDto);
}

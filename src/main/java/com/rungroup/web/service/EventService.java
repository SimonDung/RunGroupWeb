package com.rungroup.web.service;

import com.rungroup.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findById(Long eventId);

    void updateEvent(EventDto eventDto);

    void eventDelete(Long eventId);
}

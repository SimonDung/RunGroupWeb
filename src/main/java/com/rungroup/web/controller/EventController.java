package com.rungroup.web.controller;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Event;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.EventService;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public String eventList(Model model) {
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/event/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event",event);
        return "event-create";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @ModelAttribute("event")EventDto eventDto, Model model) {
        eventService.createEvent(clubId,eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("/events/{eventId}/view")
    public String eventDetail(@PathVariable("eventId") Long eventId, Model model) {
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findById(eventId);
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("club", eventDto);
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "event-detail";
    }

    @GetMapping("/events/{eventId}/edit")
    public String eventEdit(@PathVariable("eventId") Long eventId, Model model) {
        EventDto eventDto = eventService.findById(eventId);
        model.addAttribute("event", eventDto);
        return "event-edit";
    }

    @PostMapping("/events/{eventId}/update")
    public String eventUpdate(@PathVariable("eventId") Long eventId,
                              @Valid @ModelAttribute("event") EventDto eventDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "event-edit";
        }
        EventDto event = eventService.findById(eventId);
        eventDto.setId(eventId);
        eventDto.setClub(event.getClub());
        eventService.updateEvent(eventDto);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String eventDelete(@PathVariable("eventId") Long eventId) {
        eventService.eventDelete(eventId);
        return "redirect:/events";
    }
}

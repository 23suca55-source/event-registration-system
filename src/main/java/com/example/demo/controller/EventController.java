package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public EventController(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    // GET events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // ADD event
    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // UPDATE event
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {

        Event e = eventRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        e.setName(event.getName());
        e.setDate(event.getDate());
        e.setLocation(event.getLocation());

        return eventRepository.save(e);
    }

    // DELETE event
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        registrationRepository.deleteByEventId(id);
        eventRepository.deleteById(id);
    }
}
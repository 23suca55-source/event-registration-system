package com.example.demo.controller;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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

        Event e = eventRepository.findById(id).orElseThrow();

        e.setName(event.getName());
        e.setDate(event.getDate());
        e.setLocation(event.getLocation());

        return eventRepository.save(e);
    }

    // DELETE event
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }
}
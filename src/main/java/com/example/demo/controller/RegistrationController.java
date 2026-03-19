package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RegistrationRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationController(RegistrationRepository registrationRepository,
                                  EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    // REGISTER
    @PostMapping("/register")
    public Registration register(@RequestBody RegistrationRequest request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Registration registration = new Registration();
        registration.setName(request.getName());
        registration.setEmail(request.getEmail());
        registration.setPhone(request.getPhone());
        registration.setEvent(event);

        return registrationRepository.save(registration);
    }

    // GET REGISTRATIONS
    @GetMapping("/registrations")
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    // TEST
    @GetMapping("/test")
    public String testRegister() {

        List<Event> events = eventRepository.findAll();

        if (events.isEmpty()) {
            return "❌ No events found. Open /api/events first";
        }

        Event event = events.get(0);

        Registration r = new Registration();
        r.setName("Browser User");
        r.setEmail("browser@test.com");
        r.setPhone("1234567890");
        r.setEvent(event);

        registrationRepository.save(r);

        return "✅ Registration saved for event: " + event.getName();
    }
}
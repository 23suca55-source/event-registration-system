package com.example.demo.controller;
import java.util.List;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.model.Event;
import com.example.demo.model.Registration;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RegistrationRepository;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/register")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationController(RegistrationRepository registrationRepository,
                                  EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping
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
    @GetMapping
    public List<Event> getAllEvents() {

        if (eventRepository.count() == 0) {

            eventRepository.save(new Event("Tech Talk", "2026-02-10", "Chennai"));
            eventRepository.save(new Event("AI Workshop", "2026-02-12", "Bangalore"));
            eventRepository.save(new Event("Web Development Bootcamp", "2026-02-14", "Hyderabad"));
            eventRepository.save(new Event("Cloud Computing Seminar", "2026-02-16", "Pune"));
            eventRepository.save(new Event("Data Science Meetup", "2026-02-18", "Coimbatore"));
            eventRepository.save(new Event("Cyber Security Awareness", "2026-02-20", "Trichy"));
            eventRepository.save(new Event("Java Full Stack Training", "2026-02-22", "Chennai"));
            eventRepository.save(new Event("Python for Beginners", "2026-02-24", "Madurai"));
            eventRepository.save(new Event("Startup & Innovation Talk", "2026-02-26", "Salem"));
            eventRepository.save(new Event("Mobile App Development", "2026-02-28", "Erode"));
            eventRepository.save(new Event("UI/UX Design Workshop", "2026-03-02", "Bangalore"));
            eventRepository.save(new Event("DevOps Fundamentals", "2026-03-04", "Hyderabad"));
            eventRepository.save(new Event("Machine Learning Basics", "2026-03-06", "Chennai"));
            eventRepository.save(new Event("IoT & Smart Systems", "2026-03-08", "Tirunelveli"));
            eventRepository.save(new Event("Final Year Project Guidance", "2026-03-10", "Online"));
        }

        return eventRepository.findAll();
    }
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
    @GetMapping("/all")
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }


}




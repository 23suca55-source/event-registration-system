package com.example.demo;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	@Bean
	CommandLineRunner loadEvents(EventRepository eventRepository) {
		return args -> {

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

				System.out.println("Events inserted");
			}
		};
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

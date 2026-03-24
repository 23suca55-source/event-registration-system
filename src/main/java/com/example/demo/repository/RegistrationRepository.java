package com.example.demo.repository;

import com.example.demo.model.Registration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	@Modifying
	@Transactional
	@Query("delete from Registration r where r.event.id = :eventId")
	void deleteByEventId(@Param("eventId") Long eventId);
}

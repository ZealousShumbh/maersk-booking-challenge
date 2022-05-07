package com.maersk.bookingapp.externalapiclients.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.bookingapp.externalapiclients.repository.SpaceAvailabilityRepository;

@RestController
@RequestMapping("/api/bookings/checkAvailable")
public class SpaceAvailabilityController {
	
	@Autowired
	SpaceAvailabilityRepository spaceAvailabilityRepository;
	
	@GetMapping
    public String getAvailableSpace() {
        return spaceAvailabilityRepository.getAvailableSpace();
    }
}

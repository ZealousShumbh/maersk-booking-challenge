package com.maersk.bookingapp.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.maersk.bookingapp.entity.BookingEntity;
import com.maersk.bookingapp.entity.ContainerTypeEnum;
import com.maersk.bookingapp.model.AvailabilityModel;
import com.maersk.bookingapp.service.BookingService;

@RestController
@RequestMapping(value = { "/api/booking", "/api/bookings" })
public class BookingController {
	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@PostMapping(value = { "/addbooking", "/addbookings" })
	public ResponseEntity<Object> createBooking(@Valid @RequestBody BookingEntity entity) {
		logger.info("PostMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		return (createBooking(entity, (long) 0));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = { "/addbooking/{noOfContainers}", "/addbookings/{noOfContainers}" })
	public ResponseEntity<Object> createBooking(@Valid @RequestBody BookingEntity entity,
			@PathVariable Long noOfContainers) {
		logger.info("PostMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		List<BookingEntity> entities = bookingService.createBooking(entity, noOfContainers);

		logger.info("entities = " + entities);

		ObjectMapper mapper = new ObjectMapper();
		if (noOfContainers < 1) {
			ObjectNode rootNode = mapper.createObjectNode();
			rootNode.set("bookingRef", mapper.convertValue(entities.get(0).getBookingRef(), JsonNode.class));

			return new ResponseEntity<Object>(rootNode, HttpStatus.CREATED);
		} else {
			ArrayNode arrayNode = mapper.createArrayNode();

			entities.forEach(currentEntity -> {
				arrayNode.add(mapper.createObjectNode().set("bookingRef",
						mapper.convertValue(currentEntity.getBookingRef(), JsonNode.class)));
			});

			return new ResponseEntity<Object>(arrayNode, HttpStatus.CREATED);
		}
	}

	@GetMapping("/getallbookings")
	public List<BookingEntity> getAllInventories() {
		logger.info("GetMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		return bookingService.getAllBookings();
	}

	@GetMapping(value = { "/getbookingbyid/{id}", "/getbookingsbyid/{id}" })
	public BookingEntity getBookingById(@PathVariable UUID id) {
		logger.info("GetMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		return bookingService.getBookingById(id);
	}
	
	@GetMapping(value = { "/getbookingbycontainertype/{containerType}", "/getbookingsbycontainertype/{containerType}" })
	public List<BookingEntity> getBookingByContainerType(@PathVariable ContainerTypeEnum containerType) {
		logger.info("GetMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		return bookingService.getBookingByContainerType(containerType);
	}
	
	@PostMapping(value = { "/getavailability" })
	public AvailabilityModel getAvailability(@Valid @RequestBody BookingEntity entity) {
		logger.info("GetMapping Request received from - {}",
				ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

		Long availableSpace = bookingService.getAvailability(entity);
		boolean isSpaceAvailable = false;

		if (availableSpace > 0)
			isSpaceAvailable = true;

		AvailabilityModel availabilityModel = new AvailabilityModel(isSpaceAvailable);
		
		return availabilityModel;
	}
}

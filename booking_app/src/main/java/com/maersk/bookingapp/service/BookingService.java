package com.maersk.bookingapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.maersk.bookingapp.entity.BookingEntity;
import com.maersk.bookingapp.entity.ContainerTypeEnum;
import com.maersk.bookingapp.exception.NoDataFoundException;
import com.maersk.bookingapp.exception.ResourceNotFoundException;
import com.maersk.bookingapp.model.AvailabilityModel;
import com.maersk.bookingapp.repository.BookingRepository;

@Service
public class BookingService {
	private static Logger logger = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	private BookingRepository bookingRepository;

	public BookingEntity createBooking(BookingEntity entity) {
		logger.info("Creating a new booking with values '{}'", entity);
		bookingRepository.save(entity);

		BookingEntity toReturn = new BookingEntity(entity);
		logger.info("toReturn '{}'", toReturn);
		return toReturn;
	}

	public List<BookingEntity> createBooking(BookingEntity entity, Long noOfCreations) {
		logger.info("Creating a new booking with values '{}' and noOfCreations '{}'", entity, noOfCreations);

		List<BookingEntity> entities = new ArrayList<BookingEntity>();

		if (noOfCreations == 0)
			entities.add(bookingRepository.save(entity));
		else {
			for (int i = 0; i < noOfCreations; i++) {
				entities.add(bookingRepository.save(entity));
			}
		}

		return entities;
	}

	public List<BookingEntity> getAllBookings() {
		logger.info("Getting all bookings.");

		List<BookingEntity> listToReturn = (List<BookingEntity>) bookingRepository.findAll();

		logger.info("Got {} bookings.", listToReturn.size());

		if (listToReturn != null && !listToReturn.isEmpty())
			return listToReturn;
		else
			throw new NoDataFoundException("No booking present in the database for now.");
	}

	public BookingEntity getBookingById(UUID uuid) {
		logger.info("Getting booking with id - {}.", uuid);

		BookingEntity toReturn = bookingRepository.getById(uuid);
		if (Objects.isNull(toReturn)) {
			throw new ResourceNotFoundException("No booking exists with id: " + uuid);
		} else
			return toReturn;

	}

	public List<BookingEntity> getBookingByContainerType(ContainerTypeEnum containerType) {
		logger.info("Getting all bookings with containerType - {}.", containerType);

		List<BookingEntity> listToReturn = (List<BookingEntity>) bookingRepository.findByContainerType(containerType);

		logger.info("Got {} bookings.", listToReturn.size());

		if (listToReturn != null && !listToReturn.isEmpty())
			return listToReturn;
		else
			throw new NoDataFoundException("No booking present in the database for now.");
	}

	public Long getAvailability(BookingEntity entity) {
		RestTemplate restTemplate = new RestTemplate();
		String url1 = "https://maersk.com/api/bookings/checkAvailable";
		String url2 = "http://localhost:9090/api/bookings/checkAvailable";

		// Testing
		String url = "https://www.randomnumberapi.com/api/v1.0/random?min=0&max=1&count=1";

		
		Long[] result = restTemplate.getForObject(url, Long[].class);

		logger.info("Response from Actual Test Api = " + result[0]);
		
//		String result2 = restTemplate.getForObject(url2, String.class);
//		logger.info("Response from result2 Api = " + result2);

		return result[0];
	}

}

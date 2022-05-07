package com.maersk.bookingapp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maersk.bookingapp.entity.BookingEntity;
import com.maersk.bookingapp.entity.ContainerTypeEnum;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
	BookingEntity findByBookingRef(UUID bookingRef);

	List<BookingEntity> findByContainerType(ContainerTypeEnum containertype);

}

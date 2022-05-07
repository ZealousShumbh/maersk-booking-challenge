package com.maersk.bookingapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.maersk.bookingapp.converter.ContainerTypeConverter;
import com.maersk.bookingapp.utils.BookingConstants;
import com.maersk.customvalidator.ContainerSizeConstraint;

@Entity
@Table(name = "bookings")
public class BookingEntity implements Serializable {
	private static final long serialVersionUID = -8570280101689031943L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(name = "booking_ref", unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(36)")
	private UUID bookingRef;

	@Column(name = "container_size")
	@Min(value = 0, message = "Only Positive Integers Allowed")
	@Max(value = 40, message = "Max value of 40 Allowed")
	@ContainerSizeConstraint()
	private Integer containerSize;

	@Enumerated(EnumType.STRING)
	@Convert(converter = ContainerTypeConverter.class)
	@Column(name = "container_type")
	private ContainerTypeEnum containerType;

	@Column(name = "origin")
	@Size(min = BookingConstants.MIN_ORIGIN_SIZE, max = BookingConstants.MAX_ORIGIN_SIZE, message = "Allowed Values are between "
			+ BookingConstants.MIN_ORIGIN_SIZE + " and " + BookingConstants.MAX_ORIGIN_SIZE)
	private String origin;

	@Column(name = "destination")
	@Size(min = BookingConstants.MIN_DESTINATION_SIZE, max = BookingConstants.MAX_DESTINATION_SIZE, message = "Allowed Values are between "
			+ BookingConstants.MIN_DESTINATION_SIZE + " and " + BookingConstants.MAX_DESTINATION_SIZE)
	private String destination;

	@Column(name = "quantity")
	@Min(value = BookingConstants.MIN_QUANTITY, message = "Only Positive Integers Allowed. Min Value = "
			+ BookingConstants.MIN_QUANTITY)
	@Max(value = BookingConstants.MAX_QUANTITY, message = "Max Quantity Allowed = " + BookingConstants.MAX_QUANTITY)
	private Integer quantity;

	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private Date updatedAt;

	public BookingEntity() {
	}

	public BookingEntity(UUID bookingRef,
			@Min(value = 0, message = "Only Positive Integers Allowed") @Max(value = 40, message = "Max value of 40 Allowed") Integer containerSize,
			ContainerTypeEnum containerType,
			@Size(min = 5, max = 20, message = "Allowed Values are between 5 and 20") String origin,
			@Size(min = 5, max = 20, message = "Allowed Values are between 5 and 20") String destination,
			@Min(value = 1, message = "Only Positive Integers Allowed. Min Value = 1") @Max(value = 100, message = "Max Quantity Allowed = 100") Integer quantity,
			Date timestamp, Date createdAt, Date updatedAt) {
		super();
		this.bookingRef = bookingRef;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.origin = origin;
		this.destination = destination;
		this.quantity = quantity;
		this.timestamp = timestamp;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public BookingEntity(BookingEntity entity) {
		this.bookingRef = entity.getBookingRef();
		this.containerSize = entity.getContainerSize();
		this.containerType = entity.getContainerType();
		this.origin = entity.getOrigin();
		this.destination = entity.getDestination();
		this.quantity = entity.getQuantity();
		this.timestamp = entity.getTimestamp();
	}

	public UUID getBookingRef() {
		return bookingRef;
	}

	public void setBookingRef(UUID bookingRef) {
		this.bookingRef = bookingRef;
	}

	public Integer getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(Integer containerSize) {
		this.containerSize = containerSize;
	}

	public ContainerTypeEnum getContainerType() {
		return containerType;
	}

	public void setContainerType(ContainerTypeEnum containerType) {
		this.containerType = containerType;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "BookingEntity [bookingRef=" + bookingRef + ", containerSize=" + containerSize + ", containerType="
				+ containerType + ", origin=" + origin + ", destination=" + destination + ", quantity=" + quantity
				+ ", timestamp=" + timestamp + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}

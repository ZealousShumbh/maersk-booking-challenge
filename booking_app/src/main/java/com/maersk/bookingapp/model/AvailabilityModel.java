package com.maersk.bookingapp.model;

public class AvailabilityModel {
	private Boolean available;

	public AvailabilityModel() {
	}

	public AvailabilityModel(Boolean available) {
		super();
		this.available = available;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "AvailabilityModel [available=" + available + "]";
	}

}

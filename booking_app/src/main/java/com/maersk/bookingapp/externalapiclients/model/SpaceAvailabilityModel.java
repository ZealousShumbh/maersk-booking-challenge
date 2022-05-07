package com.maersk.bookingapp.externalapiclients.model;

public class SpaceAvailabilityModel {
	private Integer availableSpace;

	public SpaceAvailabilityModel() {
	}

	public SpaceAvailabilityModel(Integer availableSpace) {
		super();
		this.availableSpace = availableSpace;
	}

	public Integer getAvailableSpace() {
		return availableSpace;
	}

	public void setAvailableSpace(Integer availableSpace) {
		this.availableSpace = availableSpace;
	}

	@Override
	public String toString() {
		return "SpaceAvailabilityModel [availableSpace=" + availableSpace + "]";
	}

}

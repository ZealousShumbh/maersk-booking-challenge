package com.maersk.bookingapp.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.maersk.bookingapp.entity.ContainerTypeEnum;

@Converter(autoApply = true)
public class ContainerTypeConverter implements AttributeConverter<ContainerTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(ContainerTypeEnum attribute) {
		if (attribute == null) {
			return null;
		}

		switch (attribute) {
		case DRY:
			return "Dry";
		case FREEZER:
			return "Freezer";
		default:
			throw new IllegalArgumentException("Unknown " + attribute);

		}
	}

	@Override
	public ContainerTypeEnum convertToEntityAttribute(String dbValue) {
		if (dbValue == null || dbValue.isEmpty()) {
			return null;
		}

		ContainerTypeEnum containerType;

		try {
			containerType = ContainerTypeEnum.valueOf(dbValue.toUpperCase());
		} catch (Exception e) {
			containerType = null;
			throw new IllegalArgumentException("Unknown " + dbValue);
		}

		return containerType;
	}
}

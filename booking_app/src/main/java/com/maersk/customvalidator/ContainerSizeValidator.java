package com.maersk.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maersk.bookingapp.utils.Apputils;

public class ContainerSizeValidator implements ConstraintValidator<ContainerSizeConstraint, Integer> {
	private static final Logger logger = LoggerFactory.getLogger(ContainerSizeValidator.class);

	@Override
	public void initialize(ContainerSizeConstraint containerSize) {
	}

	@Override
	public boolean isValid(Integer containerSizeFromRequest, ConstraintValidatorContext constraintValidatorContext) {
		boolean isValid;

		if (containerSizeFromRequest == null) {
			logger.error("ContainerSize Received from Request = " + containerSizeFromRequest);
			isValid = false;
		} else
			isValid = Apputils.getAllowedContainerSizes().contains(containerSizeFromRequest);

		if (!isValid) {
			logger.info("constraintValidatorContext = " + constraintValidatorContext);
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext
					.buildConstraintViolationWithTemplate("Value Received is " + containerSizeFromRequest
							+ ". Value(s) allowed are: " + Apputils.getAllowedContainerSizes())
					.addConstraintViolation();
		}
		return isValid;
	}
}

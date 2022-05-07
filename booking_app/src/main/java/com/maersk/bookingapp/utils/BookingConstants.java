package com.maersk.bookingapp.utils;

import com.maersk.bookingapp.entity.ContainerTypeEnum;

public class BookingConstants {
	public static final ContainerTypeEnum DEFAULT_CONTAINER_TYPE = ContainerTypeEnum.DRY;
	public static final int MIN_ORIGIN_SIZE = 5;
	public static final int MAX_ORIGIN_SIZE = 20;
	public static final int MIN_DESTINATION_SIZE = 5;
	public static final int MAX_DESTINATION_SIZE = 20;
	public static final long MIN_QUANTITY = 1L;
	public static final long MAX_QUANTITY = 100L;
}

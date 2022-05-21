package com.voucher.vouchermanagement.utils.datetime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeUtils {
	public static LocalDateTime toMicrosLocalDateTime(LocalDateTime localDateTime) {
		return localDateTime.truncatedTo(ChronoUnit.MICROS);
	}
}

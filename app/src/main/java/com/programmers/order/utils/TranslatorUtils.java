package com.programmers.order.utils;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class TranslatorUtils {
	private TranslatorUtils() {
	}

	public static UUID toUUID(byte[] bytes) {
		ByteBuffer wrap = ByteBuffer.wrap(bytes);
		return new UUID(wrap.getLong(), wrap.getLong());
	}

	public static LocalDateTime toLocalDateTIme(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}

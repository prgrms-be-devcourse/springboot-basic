package org.programmers.kdt.weekly.utils;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class UtilFunction {
	public static UUID toUUID(byte[] voucher_ids) {
		var byteBuffer = ByteBuffer.wrap(voucher_ids);

		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}

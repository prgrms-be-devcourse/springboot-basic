package org.programmers.kdt.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {
	public static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}

package org.prgms.springbootbasic.util;

import java.nio.ByteBuffer;
import java.util.UUID;

/* byte로 들어온 입력값을 UUID로 변환해 주는 CONVERTER */
public class UUIDConverter {

	private UUIDConverter() {
	}

	public static byte[] uuidToBytes(UUID uuid) {
		if (uuid == null)
			return null;
		var byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	public static UUID bytesToUUID(byte[] bytes) {
		if (bytes == null)
			return null;
		var byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

}

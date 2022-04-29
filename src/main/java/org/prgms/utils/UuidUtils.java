package org.prgms.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class UuidUtils {

    // 정적 메서드 클래스로 활용할 것임. 생성자 이용 금지
    private UuidUtils() {
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);

        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return byteBuffer.array();
    }

    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

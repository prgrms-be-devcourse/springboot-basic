package org.programmers.springorder.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtil {
    private UUIDUtil() {
    }

    ;

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] uuidToBytes(String id) {
        return id.getBytes();
    }
}

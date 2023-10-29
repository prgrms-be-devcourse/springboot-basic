package com.prgrms.springbasic.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    private UUIDUtils() {
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

package com.programmers.vouchermanagement.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {
    private UUIDConverter() {
    }

    public static UUID from(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

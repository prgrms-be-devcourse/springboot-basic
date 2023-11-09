package com.programmers.springbootbasic.common.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {
    private UUIDConverter() {
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}

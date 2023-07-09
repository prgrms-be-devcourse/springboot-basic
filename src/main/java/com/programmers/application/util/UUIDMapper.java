package com.programmers.application.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDMapper {

    private UUIDMapper() {
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] toBytes(UUID voucherId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(voucherId.getMostSignificantBits());
        byteBuffer.putLong(voucherId.getLeastSignificantBits());
        return byteBuffer.array();
    }
}

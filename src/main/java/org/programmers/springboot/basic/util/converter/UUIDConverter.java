package org.programmers.springboot.basic.util.converter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

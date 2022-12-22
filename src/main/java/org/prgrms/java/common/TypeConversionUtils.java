package org.prgrms.java.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class TypeConversionUtils {
    private TypeConversionUtils() {
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

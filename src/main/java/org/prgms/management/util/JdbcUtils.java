package org.prgms.management.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtils {
    private JdbcUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

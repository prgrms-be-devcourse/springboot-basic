package org.prgrms.kdtspringdemo.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtils {
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static String uuidToBin(UUID id) {
        return "UUID_TO_BIN('%s')".formatted(id);
    }
}

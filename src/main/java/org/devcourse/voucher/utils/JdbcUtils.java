package org.devcourse.voucher.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtils {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

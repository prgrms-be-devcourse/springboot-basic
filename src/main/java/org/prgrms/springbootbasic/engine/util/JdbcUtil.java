package org.prgrms.springbootbasic.engine.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtil {
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

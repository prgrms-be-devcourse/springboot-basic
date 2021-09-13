package org.prgms;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utils {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

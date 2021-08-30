package org.prgrms.kdt;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utility {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

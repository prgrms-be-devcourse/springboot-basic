package org.prgrms.kdt.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utility {
    // inner method
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

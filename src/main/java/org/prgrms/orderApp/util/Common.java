package org.prgrms.orderApp.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Common {
    public static UUID toUUID(byte[] bytes) {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
}
}

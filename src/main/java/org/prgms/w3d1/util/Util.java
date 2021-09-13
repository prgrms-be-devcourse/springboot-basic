package org.prgms.w3d1.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Util {
    public static UUID toUUID(byte[] customer_ids) {
        var byteBuffer = ByteBuffer.wrap(customer_ids);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

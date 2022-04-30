package org.prgms.kdt.application.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

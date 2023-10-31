package org.prgrms.vouchermanager.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
    public static UUID stringToUUID(String inputString) {
        try {
            return UUID.fromString(inputString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

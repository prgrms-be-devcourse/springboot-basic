package org.prgrms.vouchermanager.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
    public static UUID intToUUID(int value) {
        long mostSignificantBits = (value) & 0xFFFFFFFFL;
        long leastSignificantBits = 0x5E05000000000000L;

        return new UUID(mostSignificantBits, leastSignificantBits);
    }
    public static UUID stringToUUID(String inputString) {
        try {
            return UUID.fromString(inputString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

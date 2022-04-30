package org.prgrms.kdt.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {

    private UuidUtils() {

    }

    public static UUID byteToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] UuidToByte(UUID uuid) {
        return uuid.toString().getBytes();
    }
}

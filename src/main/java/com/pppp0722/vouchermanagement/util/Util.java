package com.pppp0722.vouchermanagement.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Util {

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

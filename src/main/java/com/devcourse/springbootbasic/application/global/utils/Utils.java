package com.devcourse.springbootbasic.application.global.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utils {

    private Utils() {}

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}

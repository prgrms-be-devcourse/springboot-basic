package com.mountain.voucherApp.common.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class CommonUtil {

    private CommonUtil() {}

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

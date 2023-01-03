package org.prgrms.kdt.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ConvertUtil {
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static UUID toUUID(String str) {
        try {
            return UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력된 ID가 UUID 형식이 아닙니다.", e);
        }
    }
}

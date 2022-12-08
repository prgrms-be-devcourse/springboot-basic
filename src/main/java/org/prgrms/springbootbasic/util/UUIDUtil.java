package org.prgrms.springbootbasic.util;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtil {
    private static final String pattern = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static boolean isUUID(String uuid) {
        return Pattern.matches(pattern, uuid);
    }
}

package com.programmers.part1.util;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class JdbcUtil {
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp){
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}

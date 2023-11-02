package com.programmers.vouchermanagement.global.common;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class JdbcRepositoryManager {

    public static UUID bytesToUUID(byte[] bytes) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}

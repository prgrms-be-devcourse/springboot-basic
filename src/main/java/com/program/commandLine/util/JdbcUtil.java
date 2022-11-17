package com.program.commandLine.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtil {

    private JdbcUtil() throws Exception {
        throw new Exception("유틸리티 클래스를 인스턴스화할 수 없습니다!");
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

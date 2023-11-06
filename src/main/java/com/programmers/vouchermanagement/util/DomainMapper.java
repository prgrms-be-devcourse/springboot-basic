package com.programmers.vouchermanagement.util;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DomainMapper {
    public static final String ID_KEY = "id";

    protected DomainMapper() {
    }

    protected static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static Map<String, Object> uuidToParamMap(UUID id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, id.toString().getBytes());
        return paramMap;
    }
}

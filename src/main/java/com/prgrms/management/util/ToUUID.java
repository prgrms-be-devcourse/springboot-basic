package com.prgrms.management.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ToUUID {
    public static UUID toUUId(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }
}

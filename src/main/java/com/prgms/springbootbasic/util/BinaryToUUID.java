package com.prgms.springbootbasic.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class BinaryToUUID {

    public static UUID biToUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

}

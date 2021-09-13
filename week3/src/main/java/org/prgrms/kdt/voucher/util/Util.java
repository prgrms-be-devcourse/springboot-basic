package org.prgrms.kdt.voucher.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Util {
    public static UUID toUUID(byte[] bytes) {
        if(bytes==null) return null;

        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

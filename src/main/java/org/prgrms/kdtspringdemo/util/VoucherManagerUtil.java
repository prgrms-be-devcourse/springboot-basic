package org.prgrms.kdtspringdemo.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class VoucherManagerUtil {
    public static UUID toUUID(byte[] bytes) {
        var byterBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byterBuffer.getLong(), byterBuffer.getLong());
    }
}
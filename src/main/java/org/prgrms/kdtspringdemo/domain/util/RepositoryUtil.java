package org.prgrms.kdtspringdemo.domain.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class RepositoryUtil {

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

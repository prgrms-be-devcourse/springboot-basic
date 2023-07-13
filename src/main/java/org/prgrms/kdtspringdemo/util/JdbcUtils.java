package org.prgrms.kdtspringdemo.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class JdbcUtils {
    public static final int SUCCESS_SAVE_QUERY = 1;
    public static final int NOT_FOUND_UPDATE_QUERY = 0;

    public static UUID toUUID(byte[] bytes) {
      var byteBuffer = ByteBuffer.wrap(bytes);
      return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

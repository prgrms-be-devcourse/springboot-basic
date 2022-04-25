package org.prgrms.kdt.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

  private UUIDUtils() {
  }

  public static UUID toUUID(byte[] bytes) {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }
}
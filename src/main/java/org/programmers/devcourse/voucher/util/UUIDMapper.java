package org.programmers.devcourse.voucher.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDMapper {

  private UUIDMapper() {
  }

  public static byte[] toBytes(UUID uuid) {
    return ByteBuffer.allocate(16).putLong(uuid.getMostSignificantBits())
        .putLong(uuid.getLeastSignificantBits()).array();
  }

  public static UUID fromBytes(byte[] bytes) {
    var buffer = ByteBuffer.wrap(bytes);

    return new UUID(buffer.getLong(), buffer.getLong());

  }

}

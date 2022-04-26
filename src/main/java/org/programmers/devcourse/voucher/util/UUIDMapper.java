package org.programmers.devcourse.voucher.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDMapper {

  private UUIDMapper() {
  }

  public static byte[] toBytes(UUID uuid) {

    return uuid.toString().getBytes(StandardCharsets.UTF_8);
  }

  public static UUID fromBytes(byte[] bytes) {
    var buffer = ByteBuffer.wrap(bytes);
    return new UUID(buffer.getLong(), buffer.getLong());
  }

}

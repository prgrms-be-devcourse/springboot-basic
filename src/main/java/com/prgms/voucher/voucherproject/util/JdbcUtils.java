package com.prgms.voucher.voucherproject.util;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class JdbcUtils {

  public static UUID toUUID(byte[] bytes) {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }

  public static byte[] UUID_TO_BIN(UUID uuid) {
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
    byteBuffer.putLong(uuid.getMostSignificantBits());
    byteBuffer.putLong(uuid.getLeastSignificantBits());
    return byteBuffer.array();
  }

  public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
    return timestamp != null ? timestamp.toLocalDateTime() : null;
  }

}

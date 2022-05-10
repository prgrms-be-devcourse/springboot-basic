package org.programmers.kdt.weekly.utils;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;

public class UtilFunction {
    public static UUID toUUID(byte[] voucher_ids) {
        var byteBuffer = ByteBuffer.wrap(voucher_ids);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static Optional<UUID> validateUUID(String userInputUUID) {
        try {
            var uuid = UUID.fromString(userInputUUID);

            return Optional.of(uuid);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}

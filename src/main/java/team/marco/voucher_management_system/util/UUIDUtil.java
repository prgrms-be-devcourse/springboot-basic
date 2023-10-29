package team.marco.voucher_management_system.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class UUIDUtil {
    private UUIDUtil() {
        // Don't let anyone instantiate this class.
    }

    public static UUID bytesToUUID(byte[] bytes) {
        if(bytes == null) return null;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

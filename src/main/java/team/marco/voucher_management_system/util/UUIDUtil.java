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

    public static UUID stringToUUID(String string) {
        return UUID.fromString(string);
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
}

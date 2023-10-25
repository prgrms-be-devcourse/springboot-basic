package devcourse.springbootbasic.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtil {

    private UUIDUtil() {
        throw new AssertionError();
    }

    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static UUID byteToUUID(byte[] uuidBytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidBytes);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    public static UUID stringToUUID(String uuidStr) {
        return UUID.fromString(uuidStr);
    }
}

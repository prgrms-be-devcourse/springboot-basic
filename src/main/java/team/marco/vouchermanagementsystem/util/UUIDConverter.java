package team.marco.vouchermanagementsystem.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class UUIDConverter {
    private UUIDConverter() {
    }

    public static UUID convert(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

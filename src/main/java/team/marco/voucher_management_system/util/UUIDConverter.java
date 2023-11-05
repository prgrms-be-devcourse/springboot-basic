package team.marco.voucher_management_system.util;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.UUID;

public final class UUIDConverter {
    private UUIDConverter() {
    }

    public static UUID convert(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        try {
            return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        } catch (BufferUnderflowException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static UUID convert(String string) {
        return convert(string.getBytes());
    }
}

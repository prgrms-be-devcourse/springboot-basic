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
            throw new IllegalArgumentException("UUID는 16Byte여야 합니다.");
        }
    }

    public static UUID convert(String string) {
        return UUID.fromString(string);
    }
}

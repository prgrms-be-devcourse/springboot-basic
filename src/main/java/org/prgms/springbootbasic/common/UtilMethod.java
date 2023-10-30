package org.prgms.springbootbasic.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UtilMethod {
    public static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer wrappedByte = ByteBuffer.wrap(bytes);

        return new UUID(wrappedByte.getLong(), wrappedByte.getLong());
    }

    public static UUID stringToUUID(String str){
        byte[] bytes = str.getBytes();

        return bytesToUUID(bytes);
    }
}

package org.prgrms.prgrmsspring.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class BinaryToUUIDConverter {

    public UUID run(byte[] binaryData) {
        ByteBuffer buffer = ByteBuffer.wrap(binaryData);
        long mostSignificantBits = buffer.getLong();
        long leastSignificantBits = buffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}

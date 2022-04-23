package org.prgrms.voucherapplication.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Util {

    /**
     * byte를 가지고 UUID를 만들어주는 메소드
     * @param bytes
     * @return 변환된 UUID
     */
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        // 16bits 만큼의 값을 각각 빼와서 UUID의 생성자로 넣어줘야 함
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}

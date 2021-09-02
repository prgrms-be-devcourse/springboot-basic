package org.prgrms.kdt.kdtspringorder.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {

    public static UUID generateRandomUuid() {
        return UUID.randomUUID();
    }

    /**
     * UUID 변환 메서드
     * @param bytes UUID로 변환할 데이터 바이트
     * @return
     */
    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}

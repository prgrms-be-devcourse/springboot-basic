package team.marco.voucher_management_system.util;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDUtilTest {
    @Test
    void 바이트_배열을_UUID로_변환() {
        // uuid가 바이트 배열로 주어졌을 때
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = toBytes(uuid);

        // 바이트 배열을 UUID로 변환
        UUID converted = UUIDUtil.bytesToUUID(uuidBytes);

        // 변환 전 uuid를 반환
        assertThat(converted).isEqualTo(uuid);
    }

    @Test
    void 문자열을_UUID로_변환() {
        // uuid가 문자열로 주어졌을 때
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // 문자열을 UUID로 변환
        UUID converted = UUIDUtil.stringToUUID(uuidString);

        // 변환 전 uuid를 반환
        assertThat(converted).isEqualTo(uuid);
    }

    @Test
    void UUID를_바이트_배열로_변환() {
        // uuid가 주어 졌을 때
        UUID uuid = UUID.randomUUID();

        // UUID를 바이트 배열로 변환
        byte[] converted = UUIDUtil.uuidToBytes(uuid);

        // 변환 전 uuid를 반환
        assertThat(converted).isEqualTo(toBytes(uuid));
    }

    private byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
}
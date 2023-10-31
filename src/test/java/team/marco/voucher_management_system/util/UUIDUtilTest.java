package team.marco.voucher_management_system.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDUtilTest {

    @DisplayName("바이트 배열을 UUID로 변환할 수 있다.")
    @Test
    void bytesToUUID() {
        // given
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = toBytes(uuid);

        // when
        UUID converted = UUIDUtil.bytesToUUID(uuidBytes);

        // then
        assertThat(converted).isEqualTo(uuid);
    }

    @DisplayName("문자열을 UUID로 변환할 수 있다.")
    @Test
    void stringToUUID() {
        // given
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // when
        UUID converted = UUIDUtil.stringToUUID(uuidString);

        // then
        assertThat(converted).isEqualTo(uuid);
    }

    @DisplayName("UUID를 바이트 배열로 변환할 수 있다.")
    @Test
    void uuidToBytes() {
        // given
        UUID uuid = UUID.randomUUID();

        // when
        byte[] converted = UUIDUtil.uuidToBytes(uuid);

        // then
        assertThat(converted).isEqualTo(toBytes(uuid));
    }

    private byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
}
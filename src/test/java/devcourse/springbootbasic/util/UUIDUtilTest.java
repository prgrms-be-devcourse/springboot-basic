package devcourse.springbootbasic.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDUtilTest {

    @Test
    @DisplayName("랜덤 UUID를 생성할 수 있습니다.")
    void testGenerateRandomUUID() {
        // When
        UUID randomUUID = UUIDUtil.generateRandomUUID();

        // Then
        assertThat(randomUUID).isNotNull();
    }

    @Test
    @DisplayName("byte 배열을 UUID로 변환할 수 있습니다.")
    void testByteToUUID() {
        // Given
        UUID originalUUID = UUID.randomUUID();
        byte[] uuidBytes = toByteArray(originalUUID);

        // When
        UUID convertedUUID = UUIDUtil.byteToUUID(uuidBytes);

        // Then
        assertThat(convertedUUID).isEqualTo(originalUUID);
    }

    @Test
    @DisplayName("문자열을 UUID로 변환할 수 있습니다.")
    void testStringToUUID() {
        // Given
        UUID originalUUID = UUID.randomUUID();
        String uuidStr = originalUUID.toString();

        // When
        UUID convertedUUID = UUIDUtil.stringToUUID(uuidStr);

        // Then
        assertThat(convertedUUID).isEqualTo(originalUUID);
    }

    private byte[] toByteArray(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }
}

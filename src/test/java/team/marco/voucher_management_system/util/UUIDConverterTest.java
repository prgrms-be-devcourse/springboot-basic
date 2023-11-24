package team.marco.voucher_management_system.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UUIDConverterTest {
    @Nested
    @DisplayName("byte[] to UUID 테스트")
    class TestConvertBytes {
        @Test
        void success() {
            // given
            UUID originId = UUID.randomUUID();
            byte[] uuidBytes = convertUUIDToBytes(originId);

            // when
            UUID uuid = UUIDConverter.convert(uuidBytes);

            // then
            assertThat(uuid).isEqualTo(originId);
        }

        @Test
        @DisplayName("16Byte 보다 작을 경우 예외를 발생한다.")
        void underflowException() {
            // given
            byte[] uuidBytes = new byte[8];

            // when
            ThrowingCallable target = () -> UUIDConverter.convert(uuidBytes);

            // then
            assertThatIllegalArgumentException().isThrownBy(target)
                    .withMessage("UUID는 16Byte여야 합니다.");
        }
    }

    @Test
    @DisplayName("string to UUID 테스트")
    void testConvertString() {
        // given
        UUID originId = UUID.randomUUID();
        String uuidString = originId.toString();

        // when
        UUID uuid = UUIDConverter.convert(uuidString);

        // then
        assertThat(uuid).isEqualTo(originId);
    }

    private byte[] convertUUIDToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);

        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return byteBuffer.array();
    }
}

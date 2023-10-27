package team.marco.voucher_management_system.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlacklistUserTest {
    @Test
    @DisplayName("이름이 빈 문자열이 아니면 생성에 성공해야한다.")
    void successCreation() {
        // given
        UUID id = UUID.randomUUID();
        String name = "test";

        // when
        // then
        new BlacklistUser(id, name);
    }

    @Test
    @DisplayName("이름이 빈 문자열이면 생성에 실패해야한다.")
    void failCreation() {
        UUID id = UUID.randomUUID();
        String name = "";

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> new BlacklistUser(id, name));
    }
}

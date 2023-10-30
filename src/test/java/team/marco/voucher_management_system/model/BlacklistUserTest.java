package team.marco.voucher_management_system.model;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlacklistUserTest {
    @Test
    @DisplayName("이름이 빈 문자열이 아니면 생성에 성공해야한다.")
    void testSuccessCreation() {
        // given
        UUID id = UUID.randomUUID();
        String name = "test";

        // when
        ThrowingCallable targetMethod = () -> new BlacklistUser(id, name);

        // then
        assertThatNoException().isThrownBy(targetMethod);
    }

    @Test
    @DisplayName("이름이 빈 문자열이면 생성에 실패해야한다.")
    void testFailCreation() {
        UUID id = UUID.randomUUID();
        String name = "";

        // when
        ThrowingCallable targetMethod = () -> new BlacklistUser(id, name);

        // then
        assertThatIllegalArgumentException().isThrownBy(targetMethod)
                .withMessage("이름은 빈 문자열 일 수 없습니다.");
    }

    @Test
    @DisplayName("블랙리스트 사용자의 정보에는 id와 이름이 포함되어야 한다.")
    void testGetInfo() {
        UUID id = UUID.randomUUID();
        String name = "smith";

        // when
        BlacklistUser blacklistUser = new BlacklistUser(id, name);

        // then
        String blacklistUserInfo = blacklistUser.getInfo();

        assertThat(blacklistUserInfo, Matchers.containsString(id.toString()));
        assertThat(blacklistUserInfo, Matchers.containsString(name));
    }
}

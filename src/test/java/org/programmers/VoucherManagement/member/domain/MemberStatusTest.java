package org.programmers.VoucherManagement.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberStatusTest {
    @Test
    @DisplayName("입력값에 따라 회원 상태를 반환한다. - 성공")
    void from_InputStatus_EqualsMemberStatus() {
        String input = "BLACK";
        MemberStatus memberStatus = MemberStatus.from(input);
        assertThat(memberStatus).isEqualTo(MemberStatus.BLACK);
    }

    @ParameterizedTest
    @DisplayName("잘못된 입력값을 입력하면 생성할 수 없다. - 실패")
    @CsvSource({"yellow", "block", "12"})
    void from_InputStatus_ThrowIllegalArgumentException(String inputStatus) {

        assertThatThrownBy(() -> MemberStatus.from(inputStatus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 회원 상태가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @DisplayName("MemberStatus의 모든 값을 테스트한다.")
    @EnumSource(MemberStatus.class)
    void test_MemberStatusNotNull_Success(MemberStatus memberStatus) {
        Assertions.assertThat(memberStatus).isNotNull();
    }
}

package org.programmers.VoucherManagement.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberStatusTest {
    @Test
    @DisplayName("입력값에 따라 회원 상태를 반환하는 테스트 - 성공")
    void from_InputStatus_EqualsMemberStatus() {
        String input = "BLACK";
        MemberStatus memberStatus = MemberStatus.from(input);
        assertThat(memberStatus).isEqualTo(MemberStatus.BLACK);
    }

    @Test
    @DisplayName("입력값에 따라 회원 상태를 반환하는 테스트 - 실패")
    void from_InputStatus_ThrowIllegalArgumentException() {
        String input = "yellow";

        assertThatThrownBy(() -> MemberStatus.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 회원 상태가 존재하지 않습니다.");
    }
}

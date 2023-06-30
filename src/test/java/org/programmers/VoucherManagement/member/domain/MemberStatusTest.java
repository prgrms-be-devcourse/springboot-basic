package org.programmers.VoucherManagement.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberStatusTest {
    @Test
    @DisplayName("입력값에 따라 회원 상태를 반환하는 테스트 - 성공")
    void 입력값에따라_회원상태_반환_성공(){
        String input = "BLACK";
        MemberStatus memberStatus = MemberStatus.from(input);
        assertThat(memberStatus).isEqualTo(MemberStatus.BLACK);
    }

    @Test
    @DisplayName("입력값에 따라 회원 상태를 반환하는 테스트 - 실패")
    void 입력값에따라_할인타입을_반환_실패(){
        String input = "yellow";

        assertThatThrownBy(()->MemberStatus.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 회원 상태가 존재하지 않습니다.");
    }
}

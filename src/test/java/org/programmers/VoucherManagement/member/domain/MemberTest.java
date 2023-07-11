package org.programmers.VoucherManagement.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {
    @Test
    @DisplayName("회원의 상태(black/white)를 변경할 수 있다.")
    void changeMemberStatus_MemberStatus_EqualsUpdateMember() {
        //given
        Member member = new Member(UUID.randomUUID(), "kim", MemberStatus.WHITE);

        //when
        member.changeMemberStatus(MemberStatus.BLACK);

        //then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.BLACK);
    }
}

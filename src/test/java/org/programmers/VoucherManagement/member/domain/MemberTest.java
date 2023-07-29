package org.programmers.VoucherManagement.member.domain;

import com.github.f4b6a3.ulid.UlidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {
    @Test
    @DisplayName("회원의 상태(black/white)를 변경할 수 있다.")
    void changeMemberStatus_MemberStatus_EqualsUpdateMember() {
        //given
        Member member = new Member(UlidCreator.getUlid().toString(), "kim", MemberStatus.WHITE);

        //when
        member.changeMemberStatus(MemberStatus.BLACK);

        //then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.BLACK);
    }
}

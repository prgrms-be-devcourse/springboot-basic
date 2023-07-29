package org.programmers.VoucherManagement.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.global.util.MemberConverter;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberConverterTest {
    @Test
    @DisplayName("문자열을 Member 객체로 변환할 수 있다. - 성공")
    void toMember_InputString_EqualsMember() {
        // Given
        String line = "01H6ETXP7DNQ09NXRB7D9F1ZJH,Park,BLACK";

        // When
        Member member = MemberConverter.toMember(line);

        // Then
        String expectedId = "01H6ETXP7DNQ09NXRB7D9F1ZJH";
        String expectedName = "Park";
        MemberStatus expectedStatus = MemberStatus.BLACK;

        assertThat(member.getMemberId()).isEqualTo(expectedId);
        assertThat(member.getName()).isEqualTo(expectedName);
        assertThat(member.getMemberStatus()).isEqualTo(expectedStatus);
    }
}

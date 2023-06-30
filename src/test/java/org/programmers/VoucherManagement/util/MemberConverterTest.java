package org.programmers.VoucherManagement.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.VoucherManagement.global.util.MemberConverter;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import java.util.stream.Stream;

public class MemberConverterTest {
    @Test
    @DisplayName("문자열을 Member 객체로 변환하는 테스트 - 성공")
    void 문자열을_Member객체로_변환_성공() {
        // Given
        String line = "221d0e7d-0d28-4040-9012-62004a671427,Park,BLACK";

        // When
        Member member = MemberConverter.toMember(line);

        // Then
        UUID expectedId = UUID.fromString("221d0e7d-0d28-4040-9012-62004a671427");
        String expectedName = "Park";
        MemberStatus expectedStatus = MemberStatus.BLACK;

        assertThat(member.getMemberUUID()).isEqualTo(expectedId);
        assertThat(member.getName()).isEqualTo(expectedName);
        assertThat(member.getMemberStatus()).isEqualTo(expectedStatus);
    }

}


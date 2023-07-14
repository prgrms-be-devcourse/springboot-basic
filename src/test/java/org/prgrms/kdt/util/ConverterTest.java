package org.prgrms.kdt.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ConverterTest {
    @Test
    @DisplayName("바우처 객체를 스트링으로 변환")
    void voucherToString() {
        //given
        Voucher voucher = new Voucher(VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(30.0));
        UUID uuid = voucher.getVoucherId();

        //when
        String voucherString = Converter.voucherToString(voucher);

        //then
        String expectString = MessageFormat.format("{0},{1},{2}", uuid, voucher.getVoucherType(), voucher.getDiscountPolicy().getAmount());
        assertThat(voucherString, is(expectString));
    }

    @Test
    @DisplayName("올바른 멤버 객체를 인자로 전해주어 올바른 스트링으로 변환")
    void memberToString_correctMember_correctStr() {
        //given
        UUID uuid = UUID.randomUUID();
        Member member = new Member(uuid, "abc", MemberStatus.BLACK);

        //when
        String memberString = Converter.memberToString(member);

        //then
        String expectString = MessageFormat.format("{0},{1}", uuid, member.getMemberName().getName());
        assertThat(memberString, is(expectString));
    }

    @Test
    @DisplayName("필드에 null이 포함된 올바르지 않은 멤버 객체를 인자로 전해주는 경우 EntityNotFoundException 확인")
    void memberToString_incorrectMember_EntityNotFoundException(){
        //given
        UUID uuid = UUID.randomUUID();
        Member member = new Member(uuid, "abc", MemberStatus.BLACK);
        member.setName(null);

        //when
        Exception expectException = catchException(() -> Converter.memberToString(member));

        //then
        assertThat(expectException).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("스트링을 바우처 객체로 변환")
    void stringToVoucher() {
        //given
        String voucherString = "e6801502-7989-4b9a-ac83-9bf9dc38e0b0,PERCENT,20";

        //when
        Voucher voucher = Converter.stringToVoucher(voucherString);

        //then
        String voucherId = voucher.getVoucherId().toString();
        assertThat(voucherId, is("e6801502-7989-4b9a-ac83-9bf9dc38e0b0"));
    }

    @Test
    @DisplayName("스트링을 멤버 객체로 변환")
    void stringToMember() {
        //given
        String memberString = "a5b5a660-cb51-4445-9c6b-84c7a9e2131b,James";

        //when
        Member member = Converter.stringToMember(memberString, MemberStatus.BLACK);

        //then
        String memberId = member.getMemberId().toString();
        assertThat(memberId, is("a5b5a660-cb51-4445-9c6b-84c7a9e2131b"));
    }
}
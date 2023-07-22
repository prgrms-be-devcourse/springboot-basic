package org.prgrms.kdt.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

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
    @DisplayName("멤버 객체를 스트링으로 변환")
    void memberToString() {
        //given
        UUID uuid = UUID.randomUUID();
        Member member = new Member(uuid, "abc", MemberStatus.BLACK);

        //when
        String memberString = Converter.memberToString(member);

        //then
        String expectString = MessageFormat.format("{0},{1}", uuid, member.getMemberName());
        assertThat(memberString, is(expectString));
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
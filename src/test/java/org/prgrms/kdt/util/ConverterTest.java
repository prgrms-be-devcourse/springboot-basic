package org.prgrms.kdt.util;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ConverterTest {
    @Test
    void voucherToString() {
        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(uuid);

        //when
        String voucherString = Converter.voucherToString(voucher);

        //then
        String expectString = MessageFormat.format("{0},{1},{2}", uuid, voucher.getVoucherType(), voucher.getAmount());
        assertThat(voucherString, is(expectString));
    }

    @Test
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
    void stringToVoucher() {
        //given
        String voucherString = "e6801502-7989-4b9a-ac83-9bf9dc38e0b0,PercentDiscountVoucher,20";

        //when
        Voucher voucher = Converter.stringToVoucher(voucherString);

        //then
        String voucherId = voucher.getVoucherId().toString();
        assertThat(voucherId, is("e6801502-7989-4b9a-ac83-9bf9dc38e0b0"));
    }

    @Test
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
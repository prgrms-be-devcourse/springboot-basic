package org.prgrms.kdt.util;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ConverterTest {

    @Test
    public void 바우처_스트링_변환_테스트() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID());

        String voucherString = Converter.voucherToString(voucher);
        Voucher transVoucher = Converter.stringToVoucher(voucherString);

        assertThat(transVoucher.getVoucherId(), is(voucher.voucherId));
    }

    @Test
    public void 스트링_멤버변환_테스트() {
        String stringMember = "a5b5a660-cb51-4445-9c6b-84c7a9e2131b,James";

        Member member = Converter.stringToMember(stringMember, MemberStatus.BLACK);

        assertThat(member.getMemberId(), is(UUID.fromString("a5b5a660-cb51-4445-9c6b-84c7a9e2131b")));
        assertThat(member.getMemberName(), is("James"));
    }
}
package org.programers.vouchermanagement.util;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.MemberResponse;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;

import java.util.UUID;

public class Converter {

    private static String DELIMINATOR = " |,";

    public static String toString(Voucher voucher) {
        return voucher.getId() + " " + voucher.getPolicy().toString();
    }

    public static String toString(VoucherResponse voucher) {
        return voucher.getId() + " " + voucher.getPolicy().toString();
    }

    public static String toString(MemberResponse member) {
        return member.getId() + " " + member.getStatus();
    }

    public static Voucher toVoucher(String text) {
        String[] result = text.split(DELIMINATOR);
        if (result[1].contains("FixedAmountPolicy")) {
            return new Voucher(UUID.fromString(result[0]), new FixedAmountPolicy());
        }
        return new Voucher(UUID.fromString(result[0]), new PercentDiscountPolicy());
    }

    public static Member toMember(String text) {
        String[] result = text.split(DELIMINATOR);
        return new Member(UUID.fromString(result[0]), MemberStatus.valueOf(result[1]));
    }
}

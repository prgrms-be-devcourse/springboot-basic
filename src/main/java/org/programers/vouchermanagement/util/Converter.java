package org.programers.vouchermanagement.util;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.MemberResponse;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;

import java.util.UUID;

public class Converter {

    private static String DELIMINATOR = " |,";

    private Converter() {
    }

    public static String toString(Voucher voucher) {
        return String.format("%s %s", voucher.getId() , toString(voucher.getPolicy()));
    }

    public static String toString(VoucherResponse voucher) {
        return String.format("%s %s", voucher.getId() , toString(voucher.getPolicy()));
    }

    public static String toString(VoucherPolicy policy) {
        if (policy instanceof FixedAmountPolicy) {
            return String.format("FixedAmountPolicy %s", ((FixedAmountPolicy) policy).getAmount());
        }
        return String.format("PercentDiscountPolicy %s", ((PercentDiscountPolicy) policy).getPercent());
    }

    public static String toString(MemberResponse member) {
        return String.format("%s %s", member.getId(), member.getStatus());
    }

    public static Voucher toVoucher(String text) {
        String[] result = text.split(DELIMINATOR);
        int value = Integer.parseInt(result[result.length - 1]);
        if (result[1].equals("FixedAmountPolicy")) {
            return new Voucher(UUID.fromString(result[0]), new FixedAmountPolicy(value));
        }
        return new Voucher(UUID.fromString(result[0]), new PercentDiscountPolicy(value));
    }

    public static Member toMember(String text) {
        String[] result = text.split(DELIMINATOR);
        return new Member(UUID.fromString(result[0]), MemberStatus.valueOf(result[1]));
    }
}

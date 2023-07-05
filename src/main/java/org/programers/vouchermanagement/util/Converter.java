package org.programers.vouchermanagement.util;

import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.voucher.domain.*;

import java.util.UUID;

public class Converter {

    private static String DELIMINATOR = " |,";

    private Converter() {
    }

    public static String toString(Voucher voucher) {
        return String.format("%s %s %s",
                voucher.getId() , voucher.getType(), voucher.getPolicy().getValue());
    }

    public static String toString(MemberResponse member) {
        return String.format("%s %s", member.getId(), member.getStatus());
    }

    public static Voucher toVoucher(String text) {
        String[] result = text.split(DELIMINATOR);
        int value = Integer.parseInt(result[result.length - 1]);
        if (result[1].equals("FIXED_AMOUNT")) {
            return new Voucher(UUID.fromString(result[0]), new FixedAmountPolicy(value),
                    VoucherType.FIXED_AMOUNT);
        }
        return new Voucher(UUID.fromString(result[0]), new PercentDiscountPolicy(value),
                VoucherType.PERCENT);
    }

    public static Member toMember(String text) {
        String[] result = text.split(DELIMINATOR);
        return new Member(UUID.fromString(result[0]), MemberStatus.valueOf(result[1]));
    }
}

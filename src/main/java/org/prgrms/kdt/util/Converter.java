package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public final class Converter {
    private Converter() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static String voucherToString(Voucher voucher) {
        return MessageFormat.format("{0},{1},{2}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    public static String memberToString(Member member){
        return MessageFormat.format("{0},{1}", member.getMemberId(), member.getMemberName());
    }

    public static String[] stringToArray(String originalString, String delimiter) {
        return originalString.split(delimiter);
    }

    public static Voucher stringToVoucher(String str) {
        String[] stringArr = str.split(",");
        if (stringArr[1].equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(UUID.fromString(stringArr[0]));
        }
        if (stringArr[1].equals("PercentDiscountVoucher")) {
            return new PercentDiscountVoucher(UUID.fromString(stringArr[0]));
        }
        throw new InvalidInputException();
    }

    public static Member stringToMember(String str, MemberStatus status) {
        String[] stringArr = str.split(",");
        return new Member(UUID.fromString(stringArr[0]), stringArr[1], status);
    }
}

package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.FixedDiscountPolicy;
import org.prgrms.kdt.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public final class Converter {
    private Converter() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static String voucherToString(Voucher voucher) {
        return MessageFormat.format("{0},{1},{2}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountPolicy().getAmount());
    }

    public static String memberToString(Member member) {
        try {
            return MessageFormat.format("{0},{1}", member.getMemberId(), member.getMemberName().getName());
        } catch (NullPointerException e) {
            throw new EntityNotFoundException("멤버 엔티티의 필드값에 null이 포함되어 있습니다", e);
        }
    }

    public static String[] stringToArray(String originalString, String delimiter) {
        return originalString.split(delimiter);
    }

    public static Voucher stringToVoucher(String str) {
        String[] stringArr = str.split(",");
        if (stringArr[1].equals("FIXED")) {
            return new Voucher(UUID.fromString(stringArr[0]), VoucherType.FIXED, new FixedDiscountPolicy(Double.parseDouble(stringArr[2])));
        }
        if (stringArr[1].equals("PERCENT")) {
            return new Voucher(UUID.fromString(stringArr[0]), VoucherType.PERCENT, new PercentDiscountPolicy(Double.parseDouble(stringArr[2])));
        }
        throw new InvalidInputException();
    }

    public static Member stringToMember(String str, MemberStatus status) {
        String[] stringArr = str.split(",");
        return new Member(UUID.fromString(stringArr[0]), stringArr[1], status);
    }
}

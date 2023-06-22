package org.programers.vouchermanagement.util;

import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;

import java.util.UUID;

public class VoucherConverter {

    private static String DELIMINATOR = " ";

    public static String toString(Voucher voucher) {
        return voucher.getId() + DELIMINATOR + voucher.getPolicy().toString();
    }

    public static String toString(VoucherResponse voucher) {
        return voucher.getId() + DELIMINATOR + voucher.getPolicy().toString();
    }

    public static Voucher toVoucher(String text) {
        String[] result = text.split(DELIMINATOR);
        if (result[1].contains("FixedAmountPolicy")) {
            return new Voucher(UUID.fromString(result[0]), new FixedAmountPolicy());
        }
        return new Voucher(UUID.fromString(result[0]), new PercentDiscountPolicy());
    }
}

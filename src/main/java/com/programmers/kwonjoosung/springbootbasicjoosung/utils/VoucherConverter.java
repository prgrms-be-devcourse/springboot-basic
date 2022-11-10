package com.programmers.kwonjoosung.springbootbasicjoosung.utils;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.FixedAmountDiscountVoucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.PercentDiscountVoucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;

import java.util.UUID;

public class VoucherConverter {
    private static final String SPACE = " ";
    private static final String NEXT = "\n";
    private static final String ERROR = "데이터 형식이 올바르지 않습니다.";

    public static Voucher textToVoucher(String text) {
        String[] textVoucher = text.split(SPACE);
        // 여기서 Voucher 객체 생성해도 되나요? -> 아니면 VoucherService로 넘기는게 맞는건가요?
        try {
            Voucher voucher = null;
            switch (VoucherType.getVoucherType(textVoucher[0])) {
                case FIXED ->
                        voucher = new FixedAmountDiscountVoucher(UUID.fromString(textVoucher[1]), Long.parseLong(textVoucher[2]));
                case PERCENT ->
                        voucher = new PercentDiscountVoucher(UUID.fromString(textVoucher[1]), Long.parseLong(textVoucher[2]));
            }
            if (voucher == null)
                throw new RuntimeException(ERROR);
            return voucher;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String convertText(UUID voucherId, Voucher voucher) {
        return voucher.getVoucherType().name() + SPACE +
                voucherId + SPACE +
                voucher.getDiscount() + NEXT;
    }
}

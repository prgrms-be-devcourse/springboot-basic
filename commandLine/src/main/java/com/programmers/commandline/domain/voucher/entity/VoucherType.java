package com.programmers.commandline.domain.voucher.entity;

import com.programmers.commandline.domain.voucher.entity.impl.FixedAmountVoucher;
import com.programmers.commandline.domain.voucher.entity.impl.PercentDiscountVoucher;
import com.programmers.commandline.global.factory.LoggerFactory;
import com.programmers.commandline.global.util.Verification;

import java.util.Arrays;
import java.util.function.Function;

//enum에 함수를 넣는방법
//if문을 최대한 줄이면서
//다시 의문 VoucherType에서 바우처를 생성한다는 것은 할인 금액을 입력 받아야 하는것 인데 .....console까지 책임을 가져야하나 ?

public enum VoucherType {
    FIXED_AMOUNT(1,"할인금액 : ", (discount) -> new FixedAmountVoucher(discount)),
    PERCENT_DISCOUNT(2, "할인율 : ", (discount) -> new PercentDiscountVoucher(discount));

    private final int code;
    private final   String message;
    private final Function<Long, Voucher> voucher;

    VoucherType(int code, String message, Function<Long, Voucher> voucher) {
        this.code = code;
        this.message = message;
        this.voucher = voucher;
    }

    public Voucher createVoucher(long discount) {
        return voucher.apply(discount);
    }
    public static VoucherType ofNumber(String input) {
        LoggerFactory.getLogger().info("VoucherMenu selectVoucherMenu 실행");

        int code = toCode(input); // 똥

        return Arrays.stream(VoucherType.values())
                .filter(voucherMenu -> voucherMenu.code == code)
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("잘못된 바우처를 선택하셨습니다.");
                });
    }

    private static int toCode(String input) {
        LoggerFactory.getLogger().info("VoucherMenu toCode 실행");
        Verification.validateParseToInteger(input);
        return Integer.parseInt(input);
    }

    public String getMessage() {
        return this.message;
    }
}

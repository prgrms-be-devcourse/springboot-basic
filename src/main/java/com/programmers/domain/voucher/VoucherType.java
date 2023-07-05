package com.programmers.domain.voucher;

import com.programmers.exception.EmptyException;
import com.programmers.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public enum VoucherType {
    FixedAmountVoucher("1", "fixedamountvoucher", com.programmers.domain.voucher.FixedAmountVoucher::new),
    PercentDiscountVoucher("2", "percentdiscountvoucher", com.programmers.domain.voucher.PercentDiscountVoucher::new);

    private static final Logger log = LoggerFactory.getLogger(VoucherType.class);

    private final String number;
    private final String name;
    private final VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor;

    VoucherType(String number, String name, VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor) {
        this.number = number;
        this.name = name;
        this.voucherConstructor = voucherConstructor;
    }

    public static VoucherType findVoucherType(String input) {
        checkVoucherTypeInputEmpty(input);

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.number, input) || Objects.equals(voucherType.name, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("the invalid voucher type input found. input value = {}", input);
                    return new InvalidInputException("[ERROR] 입력하신 Voucher Type 값이 유효하지 않습니다.");
                });
    }

    private static void checkVoucherTypeInputEmpty(String input) {
        if (input.isEmpty()) {
            log.error("The voucher type input not found.");
            throw new EmptyException("[ERROR] Voucher Type 값이 입력되지 않았습니다.");
        }
    }

    public static Voucher createVoucher(String voucherTypeInput, String voucherName, Long discountValue) {
        VoucherType voucherType = findVoucherType(voucherTypeInput.toLowerCase());
        return voucherType.makeVoucher(voucherName, discountValue);
    }

    public static Voucher createVoucher(String voucherTypeInput, UUID uuid, String voucherName, Long discountValue) {
        VoucherType voucherType = findVoucherType(voucherTypeInput.toLowerCase());
        return voucherType.makeVoucher(uuid, voucherName, discountValue);
    }

    private Voucher makeVoucher(String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(UUID.randomUUID(), voucherName, discountValue);
    }

    private Voucher makeVoucher(UUID uuid, String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(uuid, voucherName, discountValue);
    }
}

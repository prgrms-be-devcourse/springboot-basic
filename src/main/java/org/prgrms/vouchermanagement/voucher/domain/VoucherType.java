package org.prgrms.vouchermanagement.voucher.domain;

import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;
import org.prgrms.vouchermanagement.util.func.TriFunction;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherCreateDTO;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {

    FIXED_AMOUNT("1", 1000000, FixedAmountVoucher::new),
    PERCENT_DISCOUNT("2", 100, PercentDiscountVoucher::new),;

    private final String voucherTypeNumber;
    private final int maximumDiscountAmount;
    private final TriFunction<UUID, Integer, UUID, Voucher> voucherConstructor;

    VoucherType(String voucherTypeNumber, int maximumDiscountAmount, TriFunction<UUID, Integer, UUID, Voucher> voucherConstructor) {
        this.voucherTypeNumber = voucherTypeNumber;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.voucherConstructor = voucherConstructor;
    }

    public static Voucher createVoucher(VoucherCreateDTO voucherCreateDTO) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.isMatchVoucherType(voucherCreateDTO.getVoucherType()))
                .findAny()
                .orElseThrow(InCorrectVoucherTypeException::new)
                .voucherConstructor.apply(voucherCreateDTO.getVoucherId(), voucherCreateDTO.getDiscountAmount(), voucherCreateDTO.getCustomerId());
    }

    public static boolean isCorrectVoucherType(String voucherTypeInput) {
        return Arrays.stream(values())
                .anyMatch(voucherType -> voucherType.isMatchVoucherType(voucherTypeInput));
    }

    public static boolean isCorrectDiscountAmount(String voucherTypeNumberInput, int discountAmountInput) {
        return Arrays.stream(values())
                .filter(voucherTypeNumber -> voucherTypeNumber.isMatchVoucherType(voucherTypeNumberInput))
                .findFirst()
                .orElseThrow(InCorrectVoucherTypeException::new)
                .checkDiscountAmountRange(discountAmountInput);
    }

    private boolean checkDiscountAmountRange(int discountAmountInput) {
        return discountAmountInput >= 0 && discountAmountInput <= maximumDiscountAmount;
    }

    public boolean isMatchVoucherType(String voucherTypeInput) {
        return voucherTypeNumber.equals(voucherTypeInput) || name().equals(voucherTypeInput);
    }

    public int getMaximumDiscountAmount() {
        return maximumDiscountAmount;
    }
}

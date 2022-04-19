package org.prgrms.kdt.model.voucher;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
        "Type fixed to create a new FixedAmountVoucher",
        "(0 초과로 입력해주세요.)"),
    PERCENT_DISCOUNT("percent",
        "Type percent to create a new PercentDiscountVoucher",
        "(0 초과, 100이하로 입력해주세요.)");

    private final String voucherType;
    private final String voucherManual;
    private final String voucherValidationMessage;

    VoucherType(String voucherType, String voucherMenual, String voucherValidationMessage) {
        this.voucherType = voucherType;
        this.voucherManual = voucherMenual;
        this.voucherValidationMessage = voucherValidationMessage;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getVoucherManual() {
        return voucherManual;
    }

    public String getVoucherValidationMessage() {
        return voucherValidationMessage;
    }

    public static VoucherType getVoucherType(String voucherType) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getVoucherType().equalsIgnoreCase(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 바우처 타입입니다."));
    }

    public static String getAllVoucherManual() {
        return Stream.of(VoucherType.values())
                .map(VoucherType::getVoucherManual)
                .collect(Collectors.joining("\n"));
    }
}

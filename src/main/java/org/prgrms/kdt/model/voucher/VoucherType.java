package org.prgrms.kdt.model.voucher;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
        "Type fixed to create a new FixedAmountVoucher",
        "(0 초과로 입력해주세요.)",
        FixedAmountVoucher.class,
        FixedAmountVoucher::new),
    PERCENT_DISCOUNT("percent",
        "Type percent to create a new PercentDiscountVoucher",
        "(0 초과, 100이하로 입력해주세요.)",
        PercentDiscountVoucher.class,
        PercentDiscountVoucher::new);

    private final String voucherType;
    private final String voucherManual;
    private final String voucherValidationMessage;
    private final Class<?> voucherClass;
    private final BiFunction<UUID, Long, Voucher> createVoucher;

    VoucherType(String voucherType,
                String voucherMenual,
                String voucherValidationMessage,
                Class<?> voucherClass,
                BiFunction<UUID, Long, Voucher> createVoucher
    ) {
        this.voucherType = voucherType;
        this.voucherManual = voucherMenual;
        this.voucherValidationMessage = voucherValidationMessage;
        this.voucherClass = voucherClass;
        this.createVoucher = createVoucher;
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

    public Class<?> getVoucherClass() {
        return voucherClass;
    }

    public static Optional<VoucherType> getVoucherType(String voucherType) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getVoucherType().equalsIgnoreCase(voucherType))
                .findFirst();
    }

    public static VoucherType getVoucherType(Class<?> voucherClass) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getVoucherClass() == voucherClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 바우처 타입입니다."));
    }

    public static String getAllVoucherManual() {
        return Stream.of(VoucherType.values())
                .map(VoucherType::getVoucherManual)
                .collect(Collectors.joining("\n"));
    }

    public Voucher createVoucher(UUID voucherId, long value) {
        return this.createVoucher.apply(voucherId, value);
    }
}

package org.prgrms.kdt.kdtspringorder.common.enums;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Discountable;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 유형
 */
public enum VoucherType {

    FIX("F", "가격") {

        @Override
        public Voucher createVoucher(long discount) {
            return new FixedAmountVoucher(UUID.randomUUID(), discount);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, UUID customerId, long amount, boolean useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
            return new FixedAmountVoucher(voucherId, customerId, this, amount, useYn, createdAt, usedAt);
        }

//        @Override
//        public Long getDiscountByVoucherType(Voucher voucher) {
//            return  ((FixedAmountVoucher) voucher).getAmount();
//        }

    },

    PERCENT("P", "퍼센티지") {

        @Override
        public Voucher createVoucher(long discount) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, UUID customerId, long percent, boolean useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
            return new PercentDiscountVoucher(voucherId, customerId, this, percent, useYn, createdAt, usedAt);
        }

//        @Override
//        public Long getDiscountByVoucherType(Voucher voucher) {
//            return  ((PercentDiscountVoucher) voucher).getPercent();
//        }

    };

    private final String value; // 바우처 타입 코드
    private final String unit; // 바우처 단위

    VoucherType(String value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    /**
     * 콘솔에 입력한 번호에 맞는 VoucherType을 찾습니다.
     * @param voucherTypeFlag 바우처 타입을 나타내는 플래그
     * @return 찾은 VoucherType을 반환합니다.
     */
    public static VoucherType findVoucherType(String voucherTypeFlag) {
        Optional<VoucherType> foundVoucher = Arrays.stream(values()).filter(v -> (v.getValue().equals(voucherTypeFlag))).findFirst();
        return foundVoucher.orElseThrow(() -> new IllegalArgumentException());
    }

    /**
     * 바우처 생성 메서드 입니다.
     * @param discount 할일 금액 or %
     * @return 생성한 바우처 인스턴스를 반환합니다.
     */
    public abstract Voucher createVoucher(long discount);

    public abstract Voucher createVoucher(UUID voucherId, UUID customerId, long amount, boolean useYn, LocalDateTime createdAt, LocalDateTime usedAt);

//    public abstract Long getDiscountByVoucherType(Voucher voucher);

}

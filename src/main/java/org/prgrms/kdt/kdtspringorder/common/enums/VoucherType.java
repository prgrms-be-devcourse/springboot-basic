package org.prgrms.kdt.kdtspringorder.common.enums;

import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIX("1", "가격") {
        @Override
        public Voucher createVoucher(long discount) {
            return new FixedAmountVoucher(UUID.randomUUID(), discount);
        }
    },
    PERCENT("2", "퍼센티지") {
        @Override
        public Voucher createVoucher(long discount) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }
    };

    private final String value;
    private final String unit;

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
     * @param num 콘솔에 입력한 번호
     * @return 찾은 VoucherType을 반환합니다.
     */
    public static VoucherType findVoucher(String num) {
        Optional<VoucherType> foundVoucher = Arrays.stream(values()).filter(v -> (v.getValue().equals(num))).findFirst();
        return foundVoucher.orElseThrow(() -> new IllegalArgumentException());
    }

    /**
     * 바우처 생성 메서드 입니다.
     * @param discount 할일 금액 or %
     * @return 생성한 바우처 인스턴스를 반환합니다.
     */
    public abstract Voucher createVoucher(long discount);

}

package org.prgrms.kdt.kdtspringorder.common.enums;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIX("1", "가격"),
    PERCENT("2", "퍼센티지");

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

}

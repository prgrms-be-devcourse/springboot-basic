package org.prgrms.application.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType findBySelection(String selection){
        return Arrays.stream(values())
                .filter(s -> s.name().equals(selection.toUpperCase()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("잘못된 바우처 형식 입력입니다."));
    }


}

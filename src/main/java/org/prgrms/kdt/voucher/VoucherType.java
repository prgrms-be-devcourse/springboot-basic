package org.prgrms.kdt.voucher;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VoucherType {
    PERCENTAGE("PERCENT"), FIXED("FIXED");

    private String inputString;
    private static Map<String, VoucherType> voucherTypeMap = Arrays.stream(VoucherType.values()).collect(Collectors.toMap(o -> o.inputString, o -> o));

    VoucherType(String inputString) {
        this.inputString = inputString;
    }

    public static VoucherType findVoucher(String inputString) { // TODO: Optional 을 쓰지마
        return Optional.ofNullable(voucherTypeMap.getOrDefault(inputString, null)).orElseThrow(IllegalArgumentException::new);
    }
}

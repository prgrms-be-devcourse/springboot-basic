package org.programmers.springbootbasic.data;

import org.programmers.springbootbasic.exception.WrongTypeInputException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private static final Map<String, VoucherType> BY_TYPE = Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getType, e -> e));

    private static VoucherType filterNullInput(VoucherType voucherType) {
        if (voucherType == null) throw new WrongTypeInputException("목록에 없는 Voucher 입력 커맨드입니다.");
        return voucherType;
    }

    public static VoucherType valueOfType(String type) {
        return filterNullInput(BY_TYPE.get(type));
    }
}

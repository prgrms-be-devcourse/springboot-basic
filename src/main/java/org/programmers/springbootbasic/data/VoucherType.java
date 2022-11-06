package org.programmers.springbootbasic.data;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent"), WRONG_INPUT("wrong");

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private static final Map<String, VoucherType> BY_TYPE = Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getType, e -> e));

    private static VoucherType filterNullInput(Optional<VoucherType> optionalType) {
        if(optionalType.isEmpty()) return VoucherType.WRONG_INPUT;
        else return optionalType.get();
    }

    public static VoucherType valueOfType(String type) {
        return filterNullInput(Optional.ofNullable(BY_TYPE.get(type)));
    }
}

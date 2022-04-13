package com.blessing333.springbasic.component.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum VoucherType {
    FIXED("1","고정 금액 할인"),
    PERCENT("2","비율 할인");

    private static final Map<String,VoucherType> validVoucherTypes = initValidVoucherTypes();
    private final String optionNumber;
    private final String description;

    public static Map<String,VoucherType> getValidVoucherTypes(){
        return validVoucherTypes;
    }

    private static Map<String,VoucherType> initValidVoucherTypes(){
        return Collections.unmodifiableMap(
                Stream.of(values())
                .collect(Collectors.toMap(VoucherType::getOptionNumber, Function.identity()))
        );
    }

    public static Optional<VoucherType> fromString(String optionNumber){
        return Optional.ofNullable(validVoucherTypes.get(optionNumber));
    }

}

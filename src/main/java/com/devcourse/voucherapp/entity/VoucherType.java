package com.devcourse.voucherapp.entity;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.FixDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.PercentDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.VoucherTypeInputException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import org.apache.commons.lang3.function.TriFunction;

@Getter
public enum VoucherType {
    FIX(VoucherTypeInfo.builder()
            .number("1")
            .name("고정 할인")
            .message("\n고정 할인 금액을 입력하세요. (1이상의 자연수, 단위: 원)")
            .unit("원")
            .voucherGenerator(FixDiscountVoucher::new)
            .build()
    ),
    PERCENT(VoucherTypeInfo.builder()
            .number("2")
            .name("비율 할인")
            .message("\n비율 할인 퍼센트를 입력하세요. (1이상 100이하의 자연수, 단위: %)")
            .unit("%")
            .voucherGenerator(PercentDiscountVoucher::new)
            .build()
    );

    private static final Map<String, VoucherType> VOUCHER_TYPES = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getNumber, Function.identity())));

    private final String number;
    private final String name;
    private final String message;
    private final String unit;
    private final TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator;

    VoucherType(VoucherTypeInfo voucherTypeInfo) {
        this.number = voucherTypeInfo.getNumber();
        this.name = voucherTypeInfo.getName();
        this.message = voucherTypeInfo.getMessage();
        this.unit = voucherTypeInfo.getUnit();
        this.voucherGenerator = voucherTypeInfo.getVoucherGenerator();
    }

    public static VoucherType from(String voucherTypeNumber) {
        if (VOUCHER_TYPES.containsKey(voucherTypeNumber)) {
            return VOUCHER_TYPES.get(voucherTypeNumber);
        }

        throw new VoucherTypeInputException(voucherTypeNumber);
    }

    public Voucher makeVoucher(UUID id, String discountAmount) {
        return voucherGenerator.apply(id, this, discountAmount);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", number, name);
    }
}

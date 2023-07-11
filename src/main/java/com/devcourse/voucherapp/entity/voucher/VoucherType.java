package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.utils.TriFunction;
import com.devcourse.voucherapp.utils.exception.voucher.VoucherTypeInputException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum VoucherType {
    FIX(
            "1",
            "고정 할인",
            "\n고정 할인 금액을 입력하세요. (1이상의 자연수, 단위: 원)",
            "원",
            FixDiscountVoucher::new
    ),
    PERCENT(
            "2",
            "비율 할인",
            "\n비율 할인 퍼센트를 입력하세요. (1이상 100이하의 자연수, 단위: %)",
            "%",
            PercentDiscountVoucher::new
    );

    private static final Map<String, VoucherType> VOUCHER_TYPES = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getNumber, Function.identity())));

    private final String number;
    private final String name;
    private final String message;
    private final String unit;
    private final TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator;

    VoucherType(String number, String name, String message, String unit,
            TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator) {
        this.number = number;
        this.name = name;
        this.message = message;
        this.unit = unit;
        this.voucherGenerator = voucherGenerator;
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

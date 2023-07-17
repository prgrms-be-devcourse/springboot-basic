package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.ExceptionRule;
import com.devcourse.voucherapp.exception.VoucherException;
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
            .option("1")
            .name("고정 할인")
            .condition("1이상의 자연수")
            .unit("원")
            .voucherGenerator(FixDiscountVoucher::new)
            .build()
    ),
    PERCENT(VoucherTypeInfo.builder()
            .option("2")
            .name("비율 할인")
            .condition("1이상 100이하의 자연수")
            .unit("%")
            .voucherGenerator(PercentDiscountVoucher::new)
            .build()
    );

    private static final Map<String, VoucherType> voucherTypeMap = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getOption, Function.identity())));

    private final String option;
    private final String name;
    private final String condition;
    private final String unit;
    private final TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator;

    VoucherType(VoucherTypeInfo voucherTypeInfo) {
        this.option = voucherTypeInfo.getOption();
        this.name = voucherTypeInfo.getName();
        this.condition = voucherTypeInfo.getCondition();
        this.unit = voucherTypeInfo.getUnit();
        this.voucherGenerator = voucherTypeInfo.getVoucherGenerator();
    }

    public static VoucherType from(String voucherTypeOption) {
        if (voucherTypeMap.containsKey(voucherTypeOption)) {
            return voucherTypeMap.get(voucherTypeOption);
        }

        throw new VoucherException(ExceptionRule.VOUCHER_TYPE_INVALID, voucherTypeOption);
    }

    public Voucher makeVoucher(UUID id, String discountAmount) {
        return voucherGenerator.apply(id, this, discountAmount);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", option, name);
    }
}

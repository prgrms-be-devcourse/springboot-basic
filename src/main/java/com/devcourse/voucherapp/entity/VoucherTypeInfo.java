package com.devcourse.voucherapp.entity;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.function.TriFunction;

@Builder
@Getter
public class VoucherTypeInfo {

    private final String number;
    private final String name;
    private final String message;
    private final String unit;
    private final TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator;
}

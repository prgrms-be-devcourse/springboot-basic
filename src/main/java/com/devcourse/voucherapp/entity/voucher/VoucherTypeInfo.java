package com.devcourse.voucherapp.entity.voucher;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.function.TriFunction;

@Builder
@Getter
public class VoucherTypeInfo {

    private final String number;
    private final String name;
    private final String condition;
    private final String unit;
    private final TriFunction<UUID, VoucherType, String, Voucher> voucherGenerator;
}

package com.wonu606.vouchermanager.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherDto {

    private final String type;
    private final double discountValue;
}

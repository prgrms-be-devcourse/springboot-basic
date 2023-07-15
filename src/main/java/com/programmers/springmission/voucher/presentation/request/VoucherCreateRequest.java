package com.programmers.springmission.voucher.presentation.request;

import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherCreateRequest {

    private VoucherType voucherType;
    private long amount;
}

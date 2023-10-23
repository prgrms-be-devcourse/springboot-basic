package com.programmers.vouchermanagement.dto.voucher;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.Data;

@Data
public class CreateVoucherRequestDto {
    private VoucherType voucherType;
    private Long amount;
}

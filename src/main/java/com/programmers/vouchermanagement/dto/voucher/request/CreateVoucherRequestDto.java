package com.programmers.vouchermanagement.dto.voucher.request;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.Data;

@Data
public class CreateVoucherRequestDto {
    private VoucherType type;
    private long amount;
}

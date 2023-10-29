package com.programmers.vouchermanagement.dto.voucher;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoucherRequestDto {
    private VoucherType voucherType;
    private Long amount;
}

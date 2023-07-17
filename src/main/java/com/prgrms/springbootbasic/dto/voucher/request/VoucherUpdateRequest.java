package com.prgrms.springbootbasic.dto.voucher.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherUpdateRequest {

    private UUID voucherId;
    private long discount;
}
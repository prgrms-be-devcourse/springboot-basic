package com.prgrms.springbootbasic.dto.voucher.request;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherUpdateRequest {

    private UUID voucherId;
    private long discount;
    private VoucherType type;
}
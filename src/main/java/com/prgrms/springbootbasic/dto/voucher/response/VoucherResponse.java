package com.prgrms.springbootbasic.dto.voucher.response;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherResponse {

    private final UUID voucherId;
    private long discount;
    private final VoucherType type;
    private final LocalDateTime createAt;
}
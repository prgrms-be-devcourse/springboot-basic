package com.prgrms.springbootbasic.dto.voucher.response;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherfindResponse {

    private final UUID id;
    private final VoucherType type;
    private final long discount;
    private final LocalDateTime createAt;
}
package com.prgrms.springbootbasic.dto.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherDto {

    private final UUID id;
    private final VoucherType type;
    private final long discount;
}

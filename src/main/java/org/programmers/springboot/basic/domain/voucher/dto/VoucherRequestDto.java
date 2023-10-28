package org.programmers.springboot.basic.domain.voucher.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

import java.util.UUID;

@Builder
@Getter
public class VoucherRequestDto {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;
}

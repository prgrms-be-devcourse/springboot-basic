package org.prgms.vouchermanagement.voucher.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

@Getter
@Setter
public class VoucherDto {
    private UUID voucherId;
    private long discount;
    private VoucherType voucherType;
}

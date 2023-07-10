package org.prgms.vouchermanagement.voucher.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class VoucherDto {
    private UUID voucherId;
    private long discount;
    private VoucherType voucherType;
}

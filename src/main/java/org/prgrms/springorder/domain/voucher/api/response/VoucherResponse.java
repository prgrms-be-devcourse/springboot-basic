package org.prgrms.springorder.domain.voucher.api.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

@AllArgsConstructor
@Getter
public class VoucherResponse {

    private final UUID voucherId;

    private final long amount;

    private final LocalDateTime createdAt;

    private final VoucherType voucherType;

}

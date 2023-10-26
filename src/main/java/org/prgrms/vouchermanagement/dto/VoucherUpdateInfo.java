package org.prgrms.vouchermanagement.dto;

import java.util.UUID;

public record VoucherUpdateInfo (
        UUID voucherId,
        long amountOrPercent
) { }

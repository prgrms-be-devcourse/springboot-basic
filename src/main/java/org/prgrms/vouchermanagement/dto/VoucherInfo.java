package org.prgrms.vouchermanagement.dto;

import org.prgrms.vouchermanagement.voucher.PolicyStatus;

public record VoucherInfo(
        PolicyStatus policy,
        long amountOrPercent
) { }

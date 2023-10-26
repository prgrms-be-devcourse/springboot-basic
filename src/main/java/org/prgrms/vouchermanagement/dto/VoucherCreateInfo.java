package org.prgrms.vouchermanagement.dto;

import org.prgrms.vouchermanagement.voucher.PolicyStatus;

public record VoucherCreateInfo(
        PolicyStatus policy,
        long amountOrPercent
) { }

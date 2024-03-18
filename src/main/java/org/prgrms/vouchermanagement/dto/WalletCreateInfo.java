package org.prgrms.vouchermanagement.dto;

import java.util.UUID;

public record WalletCreateInfo (
        UUID customerId,
        UUID voucherId
) {}

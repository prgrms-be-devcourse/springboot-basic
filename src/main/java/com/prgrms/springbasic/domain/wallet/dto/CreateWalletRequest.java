package com.prgrms.springbasic.domain.wallet.dto;

import java.util.UUID;

public record CreateWalletRequest(UUID customer_id, UUID voucher_id) {
}

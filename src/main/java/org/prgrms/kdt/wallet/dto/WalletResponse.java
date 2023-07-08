package org.prgrms.kdt.wallet.dto;

import java.util.UUID;

public record WalletResponse(UUID walletId, String memberName, String voucherType, double voucherAmount) {
}

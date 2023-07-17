package com.prgmrs.voucher.dto.response;

import java.util.UUID;

public record WalletResponse(UUID voucherUuid, String username) {
}

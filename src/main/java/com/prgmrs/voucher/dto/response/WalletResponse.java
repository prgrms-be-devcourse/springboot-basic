package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Wallet;

public record WalletResponse(Wallet wallet, String username) {
}

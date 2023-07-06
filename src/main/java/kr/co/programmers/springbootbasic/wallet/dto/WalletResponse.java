package kr.co.programmers.springbootbasic.wallet.dto;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;

import java.util.List;
import java.util.UUID;

public class WalletResponse {
    private final UUID walletId;
    private final List<VoucherResponse> voucherResponses;

    public WalletResponse(UUID walletId, List<VoucherResponse> voucherResponses) {
        this.walletId = walletId;
        this.voucherResponses = voucherResponses;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public List<VoucherResponse> getVoucherResponses() {
        return voucherResponses;
    }
}

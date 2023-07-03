package kr.co.programmers.springbootbasic.wallet.dto;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.UUID;

public class WalletSaveResponseDto {
    private final String customerName;
    private final UUID walletId;
    private final UUID voucherId;
    private final VoucherType voucherType;

    public WalletSaveResponseDto(String customerName, UUID walletId, UUID voucherId, VoucherType voucherType) {
        this.customerName = customerName;
        this.walletId = walletId;
        this.voucherId = voucherId;
        this.voucherType = voucherType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}

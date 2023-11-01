package com.prgrms.vouchermanager.dto.wallet;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class WalletResponse {
    public record WalletDetailResponse(UUID walletId, UUID customerId, UUID voucherId) {
        @Builder
        public WalletDetailResponse {
        }
    }

    public record WalletVoucherListResponse(List<Voucher> vouchers) {
        @Builder
        public WalletVoucherListResponse {
        }
    }

    public record WalletCustomerListResponse(List<Customer> customers) {
        @Builder
        public WalletCustomerListResponse {
        }
    }

    public record WalletListResponse(List<Wallet> wallets) {
        @Builder
        public WalletListResponse {
        }
    }

    public static WalletDetailResponse toDetailWallet(Wallet wallet) {
        return WalletDetailResponse.builder()
                .walletId(wallet.getWalletId())
                .customerId(wallet.getCustomerId())
                .voucherId(wallet.getVoucherId())
                .build();
    }
}

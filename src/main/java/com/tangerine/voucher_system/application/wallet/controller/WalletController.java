package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface WalletController {
    void createWallet(CreateWalletRequest request);

    void updateWallet(UpdateWalletRequest request);

    void deleteWalletById(UUID walletId);

    ResponseEntity<List<VoucherResponse>> voucherListOfCustomer(UUID customerId);

    ResponseEntity<List<CustomerResponse>> customerListHasVoucher(UUID voucherId);
}

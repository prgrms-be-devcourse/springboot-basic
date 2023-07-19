package com.tangerine.voucher_system.application.wallet.controller.api;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.wallet.controller.WalletController;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletRestController implements WalletController {

    private final WalletService walletService;

    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void createWallet(CreateWalletRequest request) {
        walletService.createWallet(WalletControllerMapper.INSTANCE.requestToParam(request));
    }

    @Override
    public void updateWallet(UpdateWalletRequest request) {
        walletService.updateWallet(WalletControllerMapper.INSTANCE.requestToParam(request));
    }

    public void deleteWalletById(UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    public ResponseEntity<List<VoucherResponse>> voucherListOfCustomer(UUID customerId) {
        return ResponseEntity.ok(
                walletService.findVouchersByCustomerId(customerId)
                        .stream()
                        .map(WalletControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

    public ResponseEntity<List<CustomerResponse>> customerListHasVoucher(UUID voucherId) {
        return ResponseEntity.ok(
                walletService.findCustomersByVoucherId(voucherId)
                        .stream()
                        .map(WalletControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

}

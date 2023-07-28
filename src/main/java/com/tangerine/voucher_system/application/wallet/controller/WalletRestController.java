package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletRestController {

    private final WalletService walletService;

    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("")
    public ResponseEntity<UUID> createWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(WalletControllerMapper.INSTANCE.requestToParam(request)));
    }

    @PatchMapping("")
    public ResponseEntity<UUID> updateWallet(@RequestBody UpdateWalletRequest request) {
        return ResponseEntity.ok(walletService.updateWallet(WalletControllerMapper.INSTANCE.requestToParam(request)));
    }

    @DeleteMapping("")
    public ResponseEntity<UUID> deleteWalletById(@RequestParam(name = "id") UUID walletId) {
        return ResponseEntity.ok(walletService.deleteWalletById(walletId));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<VoucherResponse>> voucherListOfCustomer(@RequestParam(name = "id") UUID customerId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(walletService.findVouchersByCustomerId(customerId)));
    }

    @GetMapping("/voucher")
    public ResponseEntity<List<CustomerResponse>> customerListHasVoucher(@RequestParam(name = "id") UUID voucherId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(walletService.findCustomersByVoucherId(voucherId)));
    }

}

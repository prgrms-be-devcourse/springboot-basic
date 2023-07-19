package com.tangerine.voucher_system.application.wallet.controller.api;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.wallet.controller.WalletController;
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
public class WalletRestController implements WalletController {

    private final WalletService walletService;

    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    @GetMapping("/create")
    public void createWallet(@RequestBody CreateWalletRequest request) {
        walletService.createWallet(WalletControllerMapper.INSTANCE.requestToParam(request));
    }

    @Override
    @GetMapping("/update")
    public void updateWallet(@RequestBody UpdateWalletRequest request) {
        walletService.updateWallet(WalletControllerMapper.INSTANCE.requestToParam(request));
    }

    @Override
    @GetMapping("/delete/{walletId}")
    public void deleteWalletById(@PathVariable UUID walletId) {
        walletService.deleteWalletById(walletId);
    }

    @Override
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VoucherResponse>> voucherListOfCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(
                walletService.findVouchersByCustomerId(customerId)
                        .stream()
                        .map(WalletControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

    @Override
    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<CustomerResponse>> customerListHasVoucher(@PathVariable UUID voucherId) {
        return ResponseEntity.ok(
                walletService.findCustomersByVoucherId(voucherId)
                        .stream()
                        .map(WalletControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

}

package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> addWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(WalletControllerMapper.INSTANCE.requestToParam(request)));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> modifyWallet(@RequestBody UpdateWalletRequest request) {
        return ResponseEntity.ok(walletService.updateWallet(WalletControllerMapper.INSTANCE.requestToParam(request)));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> deleteWalletById(@RequestParam(name = "id") UUID walletId) {
        return ResponseEntity.ok(walletService.deleteWalletById(walletId));
    }

    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VoucherResponse>> getVoucherListOfCustomer(@RequestParam(name = "id") UUID customerId) {
        return ResponseEntity.ok(
                VoucherControllerMapper.INSTANCE.resultsToResponses(walletService.findVouchersByCustomerId(customerId)));
    }

    @GetMapping(path = "/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getCustomerListHasVoucher(@RequestParam(name = "id") UUID voucherId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(walletService.findCustomersByVoucherId(voucherId)));
    }

}

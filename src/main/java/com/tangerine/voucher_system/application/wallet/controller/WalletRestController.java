package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponses;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponses;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletRestController {

    private final WalletService walletService;
    private final WalletControllerMapper mapper;

    public WalletRestController(WalletService walletService, WalletControllerMapper mapper) {
        this.walletService = walletService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> addWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(mapper.requestToParam(request)));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> modifyWallet(@RequestBody UpdateWalletRequest request) {
        return ResponseEntity.ok(walletService.updateWallet(mapper.requestToParam(request)));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> deleteWalletById(@RequestParam(name = "id") UUID walletId) {
        return ResponseEntity.ok(walletService.deleteWalletById(walletId));
    }

    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponses> getVoucherListOfCustomer(@RequestParam(name = "id") UUID customerId) {
        return ResponseEntity.ok(
                mapper.resultsToResponses(walletService.findVouchersByCustomerId(customerId)));
    }

    @GetMapping(path = "/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponses> getCustomerListHasVoucher(@RequestParam(name = "id") UUID voucherId) {
        return ResponseEntity.ok(
                mapper.resultsToResponses(walletService.findCustomersByVoucherId(voucherId)));
    }

}

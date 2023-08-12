package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponses;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.global.exception.ResponseMessage;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponses;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletRestController {

    private final WalletService walletService;
    private final WalletControllerMapper walletMapper;
    private final VoucherControllerMapper voucherMapper;
    private final CustomerControllerMapper customerMapper;

    public WalletRestController(WalletService walletService, WalletControllerMapper walletMapper, VoucherControllerMapper voucherMapper, CustomerControllerMapper customerMapper) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.voucherMapper = voucherMapper;
        this.customerMapper = customerMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageFormat.format("{0}{1}",
                        walletService.createWallet(walletMapper.requestToParam(request)),
                        ResponseMessage.CREATE_SUCCESS.getPrompt()));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modifyWallet(@RequestBody UpdateWalletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageFormat.format("{0}{1}",
                        walletService.updateWallet(walletMapper.requestToParam(request)),
                        ResponseMessage.UPDATE_SUCCESS.getPrompt()));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteWalletById(@RequestParam(name = "id") UUID walletId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageFormat.format("{0}{1}",
                        walletService.deleteWalletById(walletId),
                        ResponseMessage.DELETE_SUCCESS.getPrompt()));
    }

    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherResponses> getVoucherListOfCustomer(@RequestParam(name = "id") UUID customerId) {
        return ResponseEntity.ok(voucherMapper.resultsToResponses(
                walletService.findVouchersByCustomerId(customerId)));
    }

    @GetMapping(path = "/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponses> getCustomerListHasVoucher(@RequestParam(name = "id") UUID voucherId) {
        return ResponseEntity.ok(customerMapper.resultsToResponses(
                walletService.findCustomersByVoucherId(voucherId)));
    }

}

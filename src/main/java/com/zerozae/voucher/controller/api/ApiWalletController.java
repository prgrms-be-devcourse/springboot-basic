package com.zerozae.voucher.controller.api;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.wallet.WalletCreateRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.service.customer.CustomerService;
import com.zerozae.voucher.service.voucher.VoucherService;
import com.zerozae.voucher.service.wallet.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class ApiWalletController {

    private final WalletService walletService;
    private final CustomerService customerService;
    private final VoucherService voucherService;

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody WalletCreateRequest walletCreateRequest) {
        return ResponseEntity.status(CREATED).body(walletService.createWallet(walletCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<WalletResponse>> findAllWallets() {
        return ResponseEntity.status(OK).body(walletService.findAllWallets());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VoucherResponse>> findWalletsByCustomerId(@PathVariable("customerId") String customerId) {
        validateCustomer(customerId);
        List<WalletResponse> wallets = walletService.findWalletByCustomerId(UUID.fromString(customerId));
        List<VoucherResponse> vouchers = wallets.stream()
                .map(wallet -> voucherService.findById(wallet.getVoucherId()))
                .toList();

        return ResponseEntity.status(OK).body(vouchers);
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<CustomerResponse>> findWalletsByVoucherId(@PathVariable("voucherId") String voucherId) {
        validateVoucher(voucherId);
        List<WalletResponse> wallets = walletService.findWalletByVoucherId(UUID.fromString(voucherId));
        List<CustomerResponse> customers = wallets.stream()
                .map(wallet -> customerService.findById(wallet.getCustomerId()))
                .toList();

        return ResponseEntity.status(OK).body(customers);
    }

    @DeleteMapping("/{customerId}/{voucherId}")
    public ResponseEntity<String> deleteWallet(@PathVariable("customerId") String customerId, @PathVariable("voucherId") String voucherId) {
        validateCustomer(customerId);
        validateVoucher(voucherId);
        walletService.deleteWalletFromCustomer(UUID.fromString(customerId), UUID.fromString(voucherId));
        return ResponseEntity.status(OK).body("완료 되었습니다.");
    }

    private CustomerResponse validateCustomer(String customerId) {
        validateInputUuid(customerId);
        return customerService.findById(UUID.fromString(customerId));
    }

    private VoucherResponse validateVoucher(String voucherId) {
        validateInputUuid(voucherId);
        return voucherService.findById(UUID.fromString(voucherId));
    }
}

package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.ApiWalletRequest;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.WalletRequest;
import org.prgrms.vouchermanager.service.VoucherService;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Slf4j
public class ApiWalletController {
    private final WalletService walletService;
    private final VoucherService voucherService;
    @PostMapping("/create")
    public ResponseEntity<WalletRequest> createWallet(@RequestBody ApiWalletRequest request) {
        UUID voucherId = request.voucherId();
        Voucher voucher = voucherService.findById(voucherId);
        WalletRequest dto = new WalletRequest(request.customerEmail(),voucher);
        return new ResponseEntity<WalletRequest>(walletService.createWallet(dto), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<WalletRequest> findByEmail(@PathVariable String email) {
        return new ResponseEntity<WalletRequest>(walletService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<WalletRequest> deleteByEmail(@PathVariable String email) {
        return new ResponseEntity<WalletRequest>(walletService.deleteByEmail(email), HttpStatus.OK);
    }
}

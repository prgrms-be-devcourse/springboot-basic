package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.common.ApiResponse;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Slf4j
public class ApiWalletApiController {
    private final WalletService walletService;

    @PostMapping("/create")
    public ApiResponse createWallet(@RequestBody WalletRequestDto request) {
        return ApiResponse.success(walletService.createWallet(request));
    }

    @GetMapping("/{email}")
    public ApiResponse findByEmail(@PathVariable String email) {
        log.info(walletService.findByEmail(email).toString());
        return ApiResponse.success(walletService.findByEmail(email));
    }

    @DeleteMapping("/delete/{email}")
    public ApiResponse deleteByEmail(@PathVariable String email) {
        return ApiResponse.success(walletService.deleteByEmail(email));
    }
}

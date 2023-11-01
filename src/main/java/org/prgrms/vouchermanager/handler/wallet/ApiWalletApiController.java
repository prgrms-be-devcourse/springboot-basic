package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
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
public class ApiWalletApiController {
    private final WalletService walletService;

    @PostMapping("/create")
    public ApiResponse createWallet(@RequestBody WalletRequestDto request) {
        return ApiResponse.success(walletService.createWallet(request));
    }

    @GetMapping("/{email}")
    public ApiResponse findByEmail(@PathVariable String email) {
        return ApiResponse.success(walletService.findByEmail(email));
    }

    @DeleteMapping("/delete/{email}")
    public ApiResponse deleteByEmail(@PathVariable String email) {
        return ApiResponse.success(walletService.deleteByEmail(email));
    }

    @GetMapping("/{voucher}")
    public ApiResponse findByVoucher(@PathVariable Voucher voucher) {
        return ApiResponse.success(walletService.findByVoucher(voucher));
    }
}

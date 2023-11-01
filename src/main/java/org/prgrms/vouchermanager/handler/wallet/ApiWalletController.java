package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.common.ApiResponse;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.ApiWalletRequestDto;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.service.VoucherService;
import org.prgrms.vouchermanager.service.WalletService;
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
    public ApiResponse createWallet(@RequestBody ApiWalletRequestDto request) {
        UUID voucherId = request.getVoucherId();
        Optional<Voucher> voucher = voucherService.findById(voucherId);
        WalletRequestDto dto = WalletRequestDto.builder().customerEmail(request.getCustomerEmail()).voucher(voucher.get()).build();
        return ApiResponse.success(walletService.createWallet(dto));
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

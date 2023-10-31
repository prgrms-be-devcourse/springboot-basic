package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletApiController {
    private final WalletService walletService;

    @PostMapping("/create")
    public WalletRequestDto createWallet(@RequestBody WalletRequestDto request) {
        return walletService.createWallet(request);
    }

    @GetMapping("/{email}")
    public Optional<Wallet> findByEmail(@PathVariable String email) {
        return walletService.findByEmail(email);
    }

    @DeleteMapping("/delete/{email}")
    public Optional<Wallet> deleteByEmail(@PathVariable String email) {
        return walletService.deleteByEmail(email);
    }

    @GetMapping("/{voucher}")
    public Optional<Wallet> findByVoucher(@PathVariable Voucher voucher) {
        return walletService.findByVoucher(voucher);
    }
}

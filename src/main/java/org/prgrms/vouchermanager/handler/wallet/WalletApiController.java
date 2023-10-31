package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletApiController {
    private final WalletService walletService;

    @PostMapping("/create")
    public WalletRequestDto createWallet(@RequestBody WalletRequestDto request) {
        return walletService.createWallet(request);
    }
}

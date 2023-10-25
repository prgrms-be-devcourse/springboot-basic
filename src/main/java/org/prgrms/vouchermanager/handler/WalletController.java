package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletController {
    private final WalletService service;
    public WalletRequestDto createWallet(WalletRequestDto requestDto){
        return service.createWallet(requestDto);
    }
    public Optional<Wallet> findByEmail(String email){
        return service.findByEmail(email);
    }
    public Optional<Wallet> findByVoucher(Voucher voucher){
        return service.findByVoucher(voucher);
    }
    public Optional<Wallet> deleteByEmail(String email){
        return service.deleteByEmail(email);
    }
}

package org.prgrms.springbasic.service.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.wallet.Wallet;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final VoucherRepository repository;

    //조회
    public List<Wallet> findWallets() {
        return repository.findWallets();
    }
}

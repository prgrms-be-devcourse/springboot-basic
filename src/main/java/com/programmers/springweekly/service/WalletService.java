package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.wallet.Wallet;
import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import com.programmers.springweekly.repository.wallet.WalletRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public void save(WalletCreateRequest walletCreateRequest) {

        customerRepository.existById(walletCreateRequest.getCustomerId());
        voucherRepository.existById(walletCreateRequest.getVoucherId());

        Wallet wallet = Wallet.builder()
                .walletId(UUID.randomUUID())
                .customerId(walletCreateRequest.getCustomerId())
                .voucherId(walletCreateRequest.getVoucherId())
                .build();

        walletRepository.save(wallet);
    }

    public WalletResponse findByCustomerId(UUID customerId) {

        Wallet wallet = walletRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NoSuchElementException("해당 고객에게 할당된 바우처가 없습니다."));

        return new WalletResponse(wallet);
    }

    public WalletsResponse findByVoucherId(UUID voucherId) {
        List<Wallet> walletList = walletRepository.findByVoucherId(voucherId);

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).toList());
    }

    public void deleteByWalletId(UUID walletId) {
        walletRepository.existByWalletId(walletId);

        walletRepository.deleteByWalletId(walletId);
    }

    public WalletsResponse findAll() {
        List<Wallet> walletList = walletRepository.findAll();

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).toList());
    }
}

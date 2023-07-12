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

        customerRepository.findById(walletCreateRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다."));

        voucherRepository.findById(walletCreateRequest.getVoucherId())
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처가 없습니다."));

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

        walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NoSuchElementException("삭제할 아이디가 저장되어 있지 않습니다."));

        walletRepository.deleteByWalletId(walletId);
    }

    public WalletsResponse findAll() {
        List<Wallet> walletList = walletRepository.findAll();

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).toList());
    }
}

package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.wallet.Wallet;
import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.repository.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void save(WalletCreateRequest walletCreateRequest) {
        Wallet wallet = Wallet.builder()
                .walletId(UUID.randomUUID())
                .customerId(walletCreateRequest.getCustomerId())
                .walletId(walletCreateRequest.getVoucherId())
                .build();

        walletRepository.save(wallet);
    }

    public WalletResponse findByCustomerId(String customerId) {
        return new WalletResponse(walletRepository.findByCustomerId(customerId));
    }

    public WalletsResponse findByVoucherId(String voucherId) {
        List<Wallet> walletList = walletRepository.findByVoucherId(voucherId);

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).collect(Collectors.toList()));
    }

    public void deleteByWalletId(String walletId) {
        walletRepository.deleteByWalletId(walletId);
    }

    public WalletsResponse findAll() {
        List<Wallet> walletList = walletRepository.findAll();

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).collect(Collectors.toList()));
    }
}

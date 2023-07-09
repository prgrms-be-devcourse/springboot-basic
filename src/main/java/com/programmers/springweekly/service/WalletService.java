package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.wallet.Wallet;
import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.repository.wallet.WalletRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void save(WalletCreateRequest walletCreateRequest) {
        Wallet wallet = Wallet.builder()
                .walletId(UUID.randomUUID())
                .customerId(walletCreateRequest.getCustomerId())
                .voucherId(walletCreateRequest.getVoucherId())
                .build();

        walletRepository.save(wallet);
    }

    public WalletResponse findByCustomerId(UUID customerId) {
        return new WalletResponse(walletRepository.findByCustomerId(customerId));
    }

    public WalletsResponse findByVoucherId(UUID voucherId) {
        List<Wallet> walletList = walletRepository.findByVoucherId(voucherId);

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).collect(Collectors.toList()));
    }

    public void deleteByWalletId(UUID walletId) {
        walletRepository.deleteByWalletId(walletId);
    }

    public WalletsResponse findAll() {
        List<Wallet> walletList = walletRepository.findAll();

        return new WalletsResponse(walletList.stream().map(WalletResponse::new).collect(Collectors.toList()));
    }
}

package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    public void addWallet(WalletCreateRequestDto walletCreateRequestDto) {
    }

    public void deleteWallet(Long walletId) {
    }

    public List<Wallet> listWalletsByCustomer(Long customerId) {
        return null;
    }

    public List<Wallet> listWalletsByVoucher(Long voucherId) {
        return null;
    }
}

package com.zerozae.voucher.service.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import com.zerozae.voucher.dto.wallet.WalletRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletResponse createWallet(WalletRequest walletRequest) {
        if(isAlreadyExistWallet(walletRequest)){
            throw ErrorMessage.error("이미 존재하는 지갑입니다.");
        }
        Wallet wallet = walletRequest.to();
        walletRepository.saveOrUpdate(wallet);

        return WalletResponse.toDto(wallet);
    }

    @Transactional(readOnly = true)
    public List<WalletResponse> findWalletByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId)
                .stream()
                .map(WalletResponse::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WalletResponse> findWalletByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId)
                .stream()
                .map(WalletResponse::toDto)
                .toList();
    }

    public void deleteWalletFromCustomer(UUID customerId, UUID voucherId) {
        walletRepository.findWallet(customerId,voucherId).orElseThrow(() -> ErrorMessage.error("지갑이 존재하지 않습니다."));
        walletRepository.deleteByAllId(customerId,voucherId);
    }

    public void deleteAllWallets() {
        walletRepository.deleteAll();
    }

    private boolean isAlreadyExistWallet(WalletRequest walletRequest) {
        Optional<Wallet> wallet = walletRepository.findWallet(walletRequest.getCustomerId(), walletRequest.getVoucherId());
        return wallet.isPresent();
    }
}

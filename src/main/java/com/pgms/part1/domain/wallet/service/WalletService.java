package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.repository.WalletRepository;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WalletService {
    private final Logger log = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final KeyGenerator keyGenerator;

    public WalletService(WalletRepository walletRepository, KeyGenerator keyGenerator) {
        this.walletRepository = walletRepository;
        this.keyGenerator = keyGenerator;
    }

    public void addWallet(WalletCreateRequestDto walletCreateRequestDto) {
        try{
            Wallet wallet = new Wallet(keyGenerator.getKey(), walletCreateRequestDto.voucherId(), walletCreateRequestDto.userId());
            walletRepository.addWallet(wallet);
            log.info("customer {} wallet adds voucher {}", walletCreateRequestDto.userId(), walletCreateRequestDto.voucherId());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void deleteWallet(Long walletId) {
        try{
            walletRepository.deleteWallet(walletId);
            log.info("wallet {} is deleted", walletId);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private static void isWalletExist(List<Wallet> walletByCustomerId) {
        if(walletByCustomerId.size() == 0)
            throw new RuntimeException("no voucher or customer for it");
    }

    @Transactional(readOnly = true)
    public List<Wallet> listWalletsByCustomer(Long customerId) {
        List<Wallet> walletByCustomerId = walletRepository.findWalletByCustomerId(customerId);
        isWalletExist(walletByCustomerId);
        return walletByCustomerId;
    }

    @Transactional(readOnly = true)
    public List<Wallet> listWalletsByVoucher(Long voucherId) {
        List<Wallet> walletByVoucherId = walletRepository.findWalletByVoucherId(voucherId);
        isWalletExist(walletByVoucherId);
        return walletByVoucherId;
    }
}

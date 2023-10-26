package com.prgms.vouchermanager.service.wallet;

import com.prgms.vouchermanager.domain.wallet.Wallet;
import com.prgms.vouchermanager.dto.CreateWalletDto;
import com.prgms.vouchermanager.repository.wallet.WalletRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.*;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet save(CreateWalletDto dto) {

        try {
            return walletRepository.save(new Wallet(null,dto.getCustomerId(), dto.getVoucherId()));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(INVALID_WALLET_INFO.getMessage());
        }
    }

    @Transactional
    public void deleteByCustomerId(Long customerId) {

        try {
            walletRepository.deleteByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException(INVALID_CUSTOMER_ID.getMessage(),0);
        }
    }

    public List<Wallet> findByCustomerId(Long customerId) {
        try {
            return walletRepository.findByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException(INVALID_CUSTOMER_ID.getMessage(),0);
        }
    }

    public Wallet findByVoucherId(UUID voucherId) {

        return walletRepository.findByVoucherId(voucherId).orElseThrow(() -> new EmptyResultDataAccessException(INVALID_VOUCHER_ID.getMessage(),0));
    }
}

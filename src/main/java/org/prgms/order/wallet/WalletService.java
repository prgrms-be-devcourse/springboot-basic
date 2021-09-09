package org.prgms.order.wallet;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) { this.walletRepository = walletRepository; }

    public Wallet insert(Wallet wallet) {
        return walletRepository.insert(wallet);
    }

    Wallet findById(UUID walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Can not find a wallet for {0}", walletId)
                ));
    }

    List<Wallet> findByCustomerId(UUID customerId){
        return walletRepository.findByCustomerId(customerId);
    }

    List<Wallet> findByVoucherId(UUID voucherId){
        return walletRepository.findByVoucherId(voucherId);
    }

    List<Wallet> findByCustomerAvailable(UUID customerId){
        return walletRepository.findByCustomerAvailable(customerId);
    }
    List<Wallet> findAll(){
        return walletRepository.findAll();
    }
    void useVoucher(Wallet wallet){
        walletRepository.useVoucher(wallet);
    }

    void  deleteByWalletId(UUID walletId){
        walletRepository.deleteByWalletId(walletId);
    }

    void  deleteByCustomerId(UUID customerId){
        walletRepository.deleteByCustomerId(customerId);
    }
    void deleteByVoucherId(UUID voucherId){
        walletRepository.deleteByVoucherId(voucherId);
    }
}

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

    public Wallet findById(UUID walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Can not find a wallet for {0}", walletId)
                ));
    }

    public List<Wallet> findByCustomerId(UUID customerId){
        return walletRepository.findByCustomerId(customerId);
    }

    public List<Wallet> findByVoucherId(UUID voucherId){
        return walletRepository.findByVoucherId(voucherId);
    }

    public List<Wallet> findByCustomerAvailable(UUID customerId){
        return walletRepository.findByCustomerAvailable(customerId);
    }
    public List<Wallet> findAll(){
        return walletRepository.findAll();
    }
    public void useVoucher(UUID walletId){
        walletRepository.useVoucher(walletId);
    }

    public void  deleteByWalletId(UUID walletId){
        walletRepository.deleteByWalletId(walletId);
    }

    public void  deleteByCustomerId(UUID customerId){
        walletRepository.deleteByCustomerId(customerId);
    }
    public void deleteByVoucherId(UUID voucherId){
        walletRepository.deleteByVoucherId(voucherId);
    }
}

package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.entity.Wallet;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.repository.CustomerRepository;
import com.prgrms.kdt.springbootbasic.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(Customer customer, Voucher voucher){
        return new Wallet(UUID.randomUUID(),customer.getCustomerId(),voucher.getVoucherId());
    }

    public Optional<Wallet> saveWallet(Wallet wallet){
        if(checkWalletDuplication(wallet)){
            return Optional.empty();
        }

        return walletRepository.saveWallet(wallet);
    }

    public boolean checkWalletDuplication(Wallet wallet){
        var foundWallet = walletRepository.getWalletWithCustomerAndVoucher(wallet.getCustomerId(),wallet.getVoucherId());

        if (foundWallet.isEmpty())
            return false;
        return true;
    }

    public List<Wallet> findWalletByVoucherId(UUID voucherId){
        return walletRepository.findWalletByVoucherId(voucherId);
    }

    public List<Wallet> findWalletByCustomerId(UUID customerId){
        return walletRepository.findWalletByCustomerId(customerId);
    }

    public List<Voucher> findVoucherByCustomerId(UUID customerId){
        return walletRepository.findVoucherByCustomerId(customerId);
    }

    public List<Customer> findCustomerByVoucherId(UUID voucherId){
        return walletRepository.findCustomerByVoucherId(voucherId);
    }

    public List<Wallet> getAllWallets(){
        return walletRepository.getAllWallets();
    }
}

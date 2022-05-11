package com.prgrms.kdt.springbootbasic.wallet.service;

import com.prgrms.kdt.springbootbasic.customer.entity.Customer;
import com.prgrms.kdt.springbootbasic.wallet.entity.Wallet;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.exception.JdbcQueryFail;
import com.prgrms.kdt.springbootbasic.exception.ResourceDuplication;
import com.prgrms.kdt.springbootbasic.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet saveWallet(UUID customerId, UUID voucherId){
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, voucherId);
        if(checkWalletDuplication(wallet)){
            throw new ResourceDuplication("동일한 Wallet이 이미 존재합니다");
        }

        var saveResult = walletRepository.saveWallet(wallet);
        if (saveResult.isEmpty())
            throw new JdbcQueryFail("Customer 저장이 실패하였습니다");

        return saveResult.get();
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

    public boolean deleteWallet(Wallet wallet){
        var findWallet = walletRepository.findWalletById(wallet.getWalletId());

        if (findWallet.isEmpty())
            return true;

        return walletRepository.deleteWallets(wallet);
    }
}

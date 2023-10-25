package com.pgms.part1.domain.wallet.repository;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.wallet.entity.Wallet;

import java.util.List;

public interface WalletRepository {

    public List<Wallet> findWalletByCustomer(Customer customer);
    public List<Wallet> findWalletByVoucher(Voucher voucher);
    public void addWallet(Wallet wallet);
    public void deleteWallet(Wallet wallet);

}

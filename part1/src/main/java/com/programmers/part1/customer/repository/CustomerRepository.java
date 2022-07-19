package com.programmers.part1.customer.repository;

import com.programmers.part1.domain.VoucherWallet;
import com.programmers.part1.domain.customer.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository <ID, T>{

    T save(T customer);
    Optional<T> findById(ID customerId);
    List<T> findAllCustomer();
    List<T> findCustomerByVoucherId(ID voucherId);
    T update(T customer);
    void deleteAll();
    void deleteById(ID customerId);
    VoucherWallet insertVoucherWallet(VoucherWallet voucherWallet);
    void deleteVoucherWalletByCustomerAndVoucherId(ID customerId, ID voucherId);
}

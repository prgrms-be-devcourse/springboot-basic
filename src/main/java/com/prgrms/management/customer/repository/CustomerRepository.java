package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);

    List<Customer> findAll();

    void deleteAll();
    void deleteById(UUID customerId);
    Customer updateName(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(UUID email);

    List<Customer> findCustomerByVoucherType(VoucherType voucherType);

    List<Customer> findBlackList();
}

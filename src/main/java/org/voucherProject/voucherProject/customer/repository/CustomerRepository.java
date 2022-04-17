package org.voucherProject.voucherProject.customer.repository;

import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByName(String customerName);

    Optional<Customer> findByEmail(String customerEmail);

    List<Customer> findByVoucherType(VoucherType voucherType);

    List<Customer> findAll();

    Customer save(Customer customer);

    void deleteAll();


}

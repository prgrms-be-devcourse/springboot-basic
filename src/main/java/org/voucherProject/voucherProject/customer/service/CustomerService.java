package org.voucherProject.voucherProject.customer.service;

import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer findById(UUID customerId);

    Customer findByName(String customerName);

    Customer findByEmail(String customerEmail);

    List<Customer> findByVoucherType(VoucherType voucherType);

    List<Customer> findAll();

    Customer save(Customer customer);

    Customer update(Customer customer);

    void deleteAll();
}

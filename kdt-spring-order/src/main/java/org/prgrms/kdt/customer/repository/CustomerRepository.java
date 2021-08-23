package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface CustomerRepository {
    void insert(Customer customer);
    List<Customer> getCustomerList();
}

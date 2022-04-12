package org.prgms.voucherProgram.repository.customer;

import java.util.List;

import org.prgms.voucherProgram.entity.customer.Customer;

public interface CustomerRepository {
    List<Customer> findBlackCustomers();
}

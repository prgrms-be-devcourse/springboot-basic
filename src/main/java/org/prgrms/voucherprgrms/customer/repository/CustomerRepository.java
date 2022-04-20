package org.prgrms.voucherprgrms.customer.repository;

import org.prgrms.voucherprgrms.customer.model.Customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    Optional<Customer> findByEmail(String email);

    Customer allocateVoucher(Customer customer);

    //TODO CUSTOMER UPDATE


}

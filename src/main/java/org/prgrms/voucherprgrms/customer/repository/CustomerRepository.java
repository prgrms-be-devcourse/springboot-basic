package org.prgrms.voucherprgrms.customer.repository;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer insert(Customer customer);

    void deleteAll();

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByVoucher(Voucher voucher);

    Customer changeVoucher(Customer customer);

    List<Customer> findAll();

}

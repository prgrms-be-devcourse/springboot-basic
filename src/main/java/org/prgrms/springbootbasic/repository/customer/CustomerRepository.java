package org.prgrms.springbootbasic.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;

public interface CustomerRepository {

    List<Customer> findAll();

    UUID save(Customer customer);

    void removeAll();

    UUID changeName(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByVoucherType(VoucherType type);
}

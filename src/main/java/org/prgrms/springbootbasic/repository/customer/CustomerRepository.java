package org.prgrms.springbootbasic.repository.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;

public interface CustomerRepository {

    List<Customer> findAll();

    UUID insert(Customer customer);

    void removeAll();

    UUID updateName(Customer customer);

    Optional<Customer> findById(UUID customerId);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByVoucherType(VoucherType type);

    UUID deleteById(UUID customerId);
}

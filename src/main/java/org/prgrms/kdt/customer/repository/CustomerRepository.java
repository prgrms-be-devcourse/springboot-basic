package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.customer.dto.CustomerSignDto;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer insert(CustomerSignDto customer);

    boolean updateRoleByEmail(Customer customer);

    Optional<Customer> findByEmail(Email email);

    List<Customer> findAll();
}
